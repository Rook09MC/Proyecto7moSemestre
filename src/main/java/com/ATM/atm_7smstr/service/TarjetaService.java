package com.ATM.atm_7smstr.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.ATM.atm_7smstr.entity.Tarjeta;
import com.ATM.atm_7smstr.entity.Account;
import com.ATM.atm_7smstr.repository.TarjetaRepository;
import com.ATM.atm_7smstr.repository.AccountRepository;

@Service
public class TarjetaService {

    @Autowired
    private TarjetaRepository tarjetaRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // ===========================
    // Método para registrar tarjeta
    // ===========================
    public void registrarTarjeta(String numeroTarjeta, String pin, Long accountId) {
        Tarjeta tarjeta = new Tarjeta();
        tarjeta.setNumeroTarjeta(numeroTarjeta);
        tarjeta.setPinHash(passwordEncoder.encode(pin)); // hash del PIN
        tarjeta.setEstado("ACTIVA");
        tarjeta.setIntentosFallidos(0);
        tarjeta.setBloqueada(false);

        // Buscar la cuenta y asignarla
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));
        tarjeta.setAccount(account);

        tarjetaRepository.save(tarjeta);
    }

    // ===========================
    // Método para validar tarjeta
    // ===========================
    public String validarTarjeta(String numeroTarjeta) {
        Optional<Tarjeta> tarjetaOpt = tarjetaRepository.findByNumeroTarjeta(numeroTarjeta);

        if (tarjetaOpt.isEmpty()) {
            return "❌ Tarjeta inexistente";
        }

        Tarjeta tarjeta = tarjetaOpt.get();

        if (tarjeta.isBloqueada()) {
            return "❌ Tarjeta bloqueada";
        }

        if (!tarjeta.getEstado().equalsIgnoreCase("ACTIVA")) {
            return "❌ Tarjeta no activa";
        }

        // Obtener el nombre del usuario asociado
        if (tarjeta.getAccount() != null && tarjeta.getAccount().getUsuario() != null) {
            String nombreUsuario = tarjeta.getAccount().getUsuario().getFirstName();
            return "✅ Tarjeta válida, bienvenido " + nombreUsuario + "!";
        }

        return "✅ Tarjeta válida, usuario no encontrado";
    }

    // ===========================
    // Obtener tarjeta con usuario
    // ===========================
    public Tarjeta obtenerTarjetaConUsuario(String numeroTarjeta) {
        return tarjetaRepository.findByNumeroTarjeta(numeroTarjeta).orElse(null);
    }

    // ===========================
    // Validar PIN de la tarjeta
    // ===========================
    public boolean verificarPin(Tarjeta tarjeta, String pinIngresado) {
        return passwordEncoder.matches(pinIngresado, tarjeta.getPinHash());
    }
}