package com.ATM.atm_7smstr.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public ApiResponse validarTarjeta(String numeroTarjeta){

        Optional<Tarjeta> tarjeta = tarjetaRepository.findByNumeroTarjeta(numeroTarjeta);

        if(tarjeta.isEmpty()){

            securityLogRepository.save(
                new SecurityLog("VALIDACION_TARJETA", "Tarjeta inexistente: " + numeroTarjeta)
            );

            return new ApiResponse("ERROR","Tarjeta inexistente");
        }

        Tarjeta t = tarjeta.get();

        // verificar si está bloqueada
        if(t.isBloqueada()){

            securityLogRepository.save(
                new SecurityLog("VALIDACION_TARJETA", "Tarjeta bloqueada: " + numeroTarjeta)
            );

            return new ApiResponse("ERROR","Tarjeta bloqueada");
        }

        // verificar estado
        if(!t.getEstado().equals("ACTIVA")){

            securityLogRepository.save(
                new SecurityLog("VALIDACION_TARJETA", "Tarjeta inactiva: " + numeroTarjeta)
            );

            return new ApiResponse("ERROR","Tarjeta inactiva");
        }

        // aumentar intentos
        int intentos = t.getIntentosFallidos() + 1;
        t.setIntentosFallidos(intentos);

        // bloquear en el intento 3
        if(intentos >= 3){
            t.setBloqueada(true);
            t.setEstado("BLOQUEADA");

            securityLogRepository.save(
                new SecurityLog("SEGURIDAD", "Tarjeta bloqueada por 3 intentos: " + numeroTarjeta)
            );
        }

        tarjetaRepository.save(t);

        securityLogRepository.save(
            new SecurityLog("VALIDACION_TARJETA", "Intento #" + intentos + " tarjeta: " + numeroTarjeta)
        );

        return new ApiResponse("OK","Tarjeta válida");
    }
    public ApiResponse validarPin(String numeroTarjeta, String pin){

    Optional<Tarjeta> tarjeta = tarjetaRepository.findByNumeroTarjeta(numeroTarjeta);

    if(tarjeta.isEmpty()){
        return new ApiResponse("ERROR","Tarjeta inexistente");
    }

    Tarjeta t = tarjeta.get();

    if(t.isBloqueada()){
        return new ApiResponse("ERROR","Tarjeta bloqueada");
    }

    // verificar pin
    if(!t.getPin().equals(pin)){

        int intentos = t.getIntentosFallidos() + 1;
        t.setIntentosFallidos(intentos);

        if(intentos >= 3){
            t.setBloqueada(true);
            t.setEstado("BLOQUEADA");
        }

        tarjetaRepository.save(t);

        return new ApiResponse("ERROR","PIN incorrecto. Intento #" + intentos);
    }

    // PIN correcto
    t.setIntentosFallidos(0);
    tarjetaRepository.save(t);

    return new ApiResponse("OK","Acceso permitido");
}
}