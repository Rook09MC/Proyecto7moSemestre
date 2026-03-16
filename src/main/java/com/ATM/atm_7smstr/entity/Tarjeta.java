package com.ATM.atm_7smstr.entity;

import jakarta.persistence.*;
import com.ATM.atm_7smstr.entity.Account;

@Entity
@Table(name = "tarjetas")
public class Tarjeta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tarjeta")
    private Long id;

    @Column(name = "numero_tarjeta", nullable = false, unique = true)
    private String numeroTarjeta;

    @Column(name = "pin_hash", nullable = false)
    private String pinHash;

    @Column(name = "estado")
    private String estado; // ACTIVA, BLOQUEADA, VENCIDA

    @Column(name = "intentos_fallidos")
    private int intentosFallidos;

    private boolean bloqueada;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    public Tarjeta() {
    }

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNumeroTarjeta() { return numeroTarjeta; }
    public void setNumeroTarjeta(String numeroTarjeta) { this.numeroTarjeta = numeroTarjeta; }

    public String getPinHash() { return pinHash; }
    public void setPinHash(String pinHash) { this.pinHash = pinHash; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public int getIntentosFallidos() { return intentosFallidos; }
    public void setIntentosFallidos(int intentosFallidos) { this.intentosFallidos = intentosFallidos; }

    public boolean isBloqueada() { return bloqueada; }
    public void setBloqueada(boolean bloqueada) { this.bloqueada = bloqueada; }

    public Account getAccount() { return account; }
    public void setAccount(Account account) { this.account = account; }

    // Método para mostrar la tarjeta enmascarada
    public String getNumeroTarjetaEnmascarado() {
        if (numeroTarjeta != null && numeroTarjeta.length() >= 4) {
            return "****-****-****-" + numeroTarjeta.substring(numeroTarjeta.length() - 4);
        }
        return numeroTarjeta;
    }
}