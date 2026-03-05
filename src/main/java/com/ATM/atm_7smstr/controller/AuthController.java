package com.ATM.atm_7smstr.controller;

import com.ATM.atm_7smstr.dto.LoginRequest;
import com.ATM.atm_7smstr.dto.ApiResponse;
import com.ATM.atm_7smstr.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @GetMapping("/validar-tarjeta")
    public ApiResponse validarTarjeta(@RequestParam String numeroTarjeta){
        return authService.validarTarjeta(numeroTarjeta);
    }

    @PostMapping("/login")
    public ApiResponse login(@RequestBody LoginRequest request){
        // Llamamos al servicio y obtenemos respuesta
        ApiResponse response = authService.validarPin(
            request.getNumeroTarjeta(),
            request.getPin()
        );

        // Log para ver qué llega
        System.out.println("Número de tarjeta: " + request.getNumeroTarjeta());
        System.out.println("PIN: " + request.getPin());
        System.out.println("Mensaje de login: " + response.getMessage());

        return response;
    }
}