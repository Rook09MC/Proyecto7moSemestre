package com.ATM.atm_7smstr.controller;

import com.ATM.atm_7smstr.dto.LoginRequest;
import com.ATM.atm_7smstr.dto.ApiResponse;
import com.ATM.atm_7smstr.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class LoginWebController {

    @Autowired
    private AuthService authService;

    // Mostrar el formulario de login
    @GetMapping("/login")
    public String mostrarLogin(Model model) {
        model.addAttribute("loginRequest", new LoginRequest());
        return "login"; // archivo login.html en templates
    }

    // Procesar el formulario
    @PostMapping("/login")
    public String procesarLogin(@ModelAttribute LoginRequest loginRequest, Model model) {
        ApiResponse response = authService.validarPin(loginRequest.getNumeroTarjeta(), loginRequest.getPin());
        System.out.println("Respuesta AuthService: " + response.getStatus() + " - " + response.getMessage());
        System.out.println("Número de tarjeta recibido: " + loginRequest.getNumeroTarjeta());
        System.out.println("PIN recibido: " + loginRequest.getPin());

        if (response.getStatus().equals("OK")) {
            model.addAttribute("mensajeExito", response.getMessage());
            return "bienvenido"; // archivo bienvenido.html
        } else {
            model.addAttribute("mensajeError", response.getMessage());
            return "login"; // volver al login con mensaje de error
        }
    }
}