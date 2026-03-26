package com.ATM.atm_7smstr.service;

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
    // REGISTRAR TARJETA
    // ===========================
    public void registrarTarjeta(String numeroTarjeta, String pin, Long accountId) {
        Tarjeta tarjeta = new Tarjeta();
        tarjeta.setNumeroTarjeta(numeroTarjeta);
        tarjeta.setPinHash(passwordEncoder.encode(pin));
        tarjeta.setEstado("ACTIVA");
        tarjeta.setIntentosFallidos(0);
        tarjeta.setBloqueada(false);

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));

        tarjeta.setAccount(account);

        tarjetaRepository.save(tarjeta);
    }

    // ===========================
    // OBTENER TARJETA CON USUARIO
    // ===========================
    public Tarjeta obtenerTarjetaConUsuario(String numeroTarjeta) {
        return tarjetaRepository.findByNumeroTarjeta(numeroTarjeta).orElse(null);
    }

    // ===========================
    // VERIFICAR PIN (opcional)
    // ===========================
    public boolean verificarPin(Tarjeta tarjeta, String pinIngresado) {
        return passwordEncoder.matches(pinIngresado, tarjeta.getPinHash());
    }
}