package com.ATM.atm_7smstr.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // 🔥 Genera getters, setters, toString, equals, hashCode
@AllArgsConstructor // 🔥 Constructor con parámetros
@NoArgsConstructor  // 🔥 Constructor vacío (necesario para Spring)
public class ApiResponse {

    private String status;
    private String message;

    // 🔥 MÉTODOS ESTÁTICOS (se mantienen, son buena práctica)
    public static ApiResponse ok(String message){
        return new ApiResponse("OK", message);
    }

    public static ApiResponse error(String message){
        return new ApiResponse("ERROR", message);
    }
}