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
        ApiResponse response = authService.validarPin(loginRequest.getNumeroTarjeta(), loginRequest.getPin());
        
        System.out.println("Respuesta AuthService: " + response.getStatus() + " - " + response.getMessage());

        if ("OK".equals(response.getStatus())) {
            // 2. Obtener el objeto Tarjeta con toda su relación (Account -> Usuario)
            Tarjeta tarjeta = tarjetaService.obtenerTarjetaConUsuario(loginRequest.getNumeroTarjeta());
            
            // 3. ENVIAR EL OBJETO AL MODELO (Esto soluciona el error de Thymeleaf)
            model.addAttribute("tarjeta", tarjeta);

            // 4. Lógica de seguridad para el mensaje de bienvenida
            if (tarjeta != null && tarjeta.getAccount() != null && tarjeta.getAccount().getUsuario() != null) {
                String nombreUsuario = tarjeta.getAccount().getUsuario().getFirstName();
                model.addAttribute("mensajeExito", "✅ ¡Bienvenido de nuevo, " + nombreUsuario + "!");
            } else {
                model.addAttribute("mensajeExito", "✅ Acceso correcto (Datos de perfil no encontrados).");
            }
            
            return "bienvenido"; 
        } else {
            // Si el PIN es incorrecto o la tarjeta no existe
            model.addAttribute("mensajeError", response.getMessage());
            // Es necesario volver a enviar el objeto de la solicitud para no romper el formulario
            model.addAttribute("loginRequest", loginRequest);
            return "login"; 
        }
    }
}