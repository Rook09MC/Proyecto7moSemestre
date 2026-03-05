package com.ATM.atm_7smstr.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ATM.atm_7smstr.entity.Tarjeta;
import com.ATM.atm_7smstr.repository.TarjetaRepository;

@Service
public class TarjetaService {

    @Autowired
    private TarjetaRepository tarjetaRepository;

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

        return "✅ Tarjeta válida, puede continuar";
    }
}