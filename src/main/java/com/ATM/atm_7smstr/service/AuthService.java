package com.ATM.atm_7smstr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.ATM.atm_7smstr.dto.ApiResponse;
import com.ATM.atm_7smstr.entity.Tarjeta;
import com.ATM.atm_7smstr.entity.SecurityLog;
import com.ATM.atm_7smstr.repository.TarjetaRepository;
import com.ATM.atm_7smstr.repository.SecurityLogRepository;

@Service
public class AuthService {

    @Autowired
    private TarjetaRepository tarjetaRepository;

    @Autowired
    private SecurityLogRepository securityLogRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // ===========================
    // 🔥 CONSTANTES (NUEVO)
    // ===========================
    private static final String ESTADO_ACTIVA = "ACTIVA";
    private static final String ESTADO_BLOQUEADA = "BLOQUEADA";

    private static final String LOG_LOGIN = "LOGIN";
    private static final String LOG_VALIDACION = "VALIDACION_TARJETA";
    private static final String LOG_SEGURIDAD = "SEGURIDAD";

    private static final int MAX_INTENTOS = 3;

    // ===========================
    // 🔥 MÉTODO REUTILIZABLE LOG (YA LO TENÍAS)
    // ===========================
    private void log(String tipo, String mensaje){
        securityLogRepository.save(new SecurityLog(tipo, mensaje));
    }

    // ===========================
    // 🔥 MÉTODO REUTILIZABLE PARA BUSCAR TARJETA (NUEVO)
    // ===========================
    private Tarjeta obtenerTarjeta(String numeroTarjeta){
        return tarjetaRepository.findByNumeroTarjeta(numeroTarjeta).orElse(null);
    }

    // ===========================
    // 🔥 MÉTODO REUTILIZABLE INTENTOS (MEJORADO)
    // ===========================
    private void manejarIntentosFallidos(Tarjeta t){
        int intentos = t.getIntentosFallidos() + 1;
        t.setIntentosFallidos(intentos);

        // 🔥 CAMBIO: uso de constante MAX_INTENTOS
        if(intentos >= MAX_INTENTOS){
            t.setBloqueada(true);
            t.setEstado(ESTADO_BLOQUEADA);

            // 🔥 CAMBIO: uso de constantes en log
            log(LOG_SEGURIDAD, "Tarjeta bloqueada por intentos: " + t.getNumeroTarjeta());
        }
    }

    // ===========================
    // VALIDAR TARJETA
    // ===========================
    public ApiResponse validarTarjeta(String numeroTarjeta){

        // 🔥 CAMBIO: uso de método reutilizable
        Tarjeta t = obtenerTarjeta(numeroTarjeta);

        if(t == null){
            log(LOG_VALIDACION, "Tarjeta inexistente: " + numeroTarjeta);
            return ApiResponse.error("Tarjeta inexistente");
        }

        if(t.isBloqueada()){
            log(LOG_VALIDACION, "Tarjeta bloqueada: " + numeroTarjeta);
            return ApiResponse.error("Tarjeta bloqueada");
        }

        // 🔥 CAMBIO: equalsIgnoreCase + constante
        if(!ESTADO_ACTIVA.equalsIgnoreCase(t.getEstado())){
            log(LOG_VALIDACION, "Tarjeta inactiva: " + numeroTarjeta);
            return ApiResponse.error("Tarjeta inactiva");
        }

        // 🔥 LIMPIO: ya no hay intentos aquí (correcto)
        log(LOG_VALIDACION, "Tarjeta válida: " + numeroTarjeta);

        return ApiResponse.ok("Tarjeta válida");
    }

    // ===========================
    // VALIDAR PIN
    // ===========================
    public ApiResponse validarPin(String numeroTarjeta, String pin){

        // 🔥 CAMBIO: uso de método reutilizable
        Tarjeta t = obtenerTarjeta(numeroTarjeta);

        if(t == null){
            log(LOG_LOGIN, "Tarjeta inexistente: " + numeroTarjeta);
            return ApiResponse.error("Tarjeta inexistente");
        }

        if(t.isBloqueada()){
            log(LOG_LOGIN, "Intento con tarjeta bloqueada: " + numeroTarjeta);
            return ApiResponse.error("Tarjeta bloqueada");
        }

        // 🔥 CAMBIO IMPORTANTE: BCrypt (ANTES era .equals)
        if(!passwordEncoder.matches(pin, t.getPinHash())){

            // 🔥 CAMBIO: método reutilizable
            manejarIntentosFallidos(t);
            tarjetaRepository.save(t);

            log(LOG_LOGIN, "PIN incorrecto intento #" + t.getIntentosFallidos() + " tarjeta: " + numeroTarjeta);
            return ApiResponse.error("PIN incorrecto. Intento #" + t.getIntentosFallidos());
        }

        // 🔥 CAMBIO: orden lógico mejorado
        log(LOG_LOGIN, "Acceso exitoso: " + numeroTarjeta);

        t.setIntentosFallidos(0);
        tarjetaRepository.save(t);

        return ApiResponse.ok("Acceso permitido");
    }
}