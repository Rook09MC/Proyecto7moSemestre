package com.ATM.atm_7smstr.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tarjetas")
public class Tarjeta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_tarjeta")
    private String numeroTarjeta;

    private String estado;

    @Column(name = "intentos_fallidos")
    private int intentosFallidos;

    private boolean bloqueada;

    @Column(name="pin")
    private String pin;

    public Tarjeta() {
    }

    public Long getId() {
        return id;
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public String getEstado() {
        return estado;
    }

    public int getIntentosFallidos() {
        return intentosFallidos;
    }

    public boolean isBloqueada() {
        return bloqueada;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setIntentosFallidos(int intentosFallidos) {
        this.intentosFallidos = intentosFallidos;
    }

    public void setBloqueada(boolean bloqueada) {
        this.bloqueada = bloqueada;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
}