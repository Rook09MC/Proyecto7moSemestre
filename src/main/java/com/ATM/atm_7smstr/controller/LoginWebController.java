package com.ATM.atm_7smstr.controller;

import com.ATM.atm_7smstr.dto.LoginRequest;
import com.ATM.atm_7smstr.entity.Tarjeta;
import com.ATM.atm_7smstr.dto.ApiResponse;
import com.ATM.atm_7smstr.service.AuthService;
import com.ATM.atm_7smstr.service.TarjetaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class LoginWebController {

    @Autowired
    private AuthService authService;

    @Autowired
    private TarjetaService tarjetaService;

    // Mostrar el formulario de login
    @GetMapping("/login")
    public String mostrarLogin(Model model) {
        model.addAttribute("loginRequest", new LoginRequest());
        return "login"; 
    }

    // Procesar el formulario
    @PostMapping("/login")
    public String procesarLogin(@ModelAttribute LoginRequest loginRequest, Model model) {
        // 1. Validar las credenciales
        ApiResponse response = authService.validarPin(
                loginRequest.getNumeroTarjeta(),
                loginRequest.getPin()
        );

        // ❌ ELIMINADO: System.out.println (código sucio)

        if ("OK".equals(response.getStatus())) {
            // 2. Obtener el objeto Tarjeta con toda su relación
            Tarjeta tarjeta = tarjetaService.obtenerTarjetaConUsuario(
                    loginRequest.getNumeroTarjeta()
            );

            // 3. Enviar objeto al modelo
            model.addAttribute("tarjeta", tarjeta);

            // 🔥 CAMBIO: variable auxiliar para simplificar lógica
            String nombreUsuario = "Usuario"; // <-- NUEVO

            // 🔥 CAMBIO: condición simplificada
            if (tarjeta != null &&
                tarjeta.getAccount() != null &&
                tarjeta.getAccount().getUsuario() != null) {

                nombreUsuario = tarjeta.getAccount().getUsuario().getFirstName(); // <-- MODIFICADO
            }

            // 🔥 CAMBIO: mensaje único (más limpio)
            model.addAttribute("mensajeExito",
                    "✅ ¡Bienvenido de nuevo, " + nombreUsuario + "!"); // <-- MODIFICADO

            return "bienvenido"; 
        } else {
            // Si el PIN es incorrecto o la tarjeta no existe
            model.addAttribute("mensajeError", response.getMessage());
            model.addAttribute("loginRequest", loginRequest);
            return "login"; 
        }
    }
}