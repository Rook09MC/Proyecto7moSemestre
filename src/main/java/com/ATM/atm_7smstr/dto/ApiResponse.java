package com.ATM.atm_7smstr.dto;

public class ApiResponse {

    private String status;  // "OK" o "ERROR"
    private String message; // mensaje para el usuario

    // 🔹 Constructor vacío necesario para Spring/Jackson
    public ApiResponse() {}

    // 🔹 Constructor con parámetros
    public ApiResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }

    // 🔹 Getters
    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    // 🔹 Setters necesarios para Jackson
    public void setStatus(String status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    // 🔹 Opcional: para depuración
    @Override
    public String toString() {
        return "ApiResponse{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}