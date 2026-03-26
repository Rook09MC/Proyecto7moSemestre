package com.ATM.atm_7smstr.entity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class TestHash {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String pin = "4567";

        String hash = encoder.encode(pin);
        System.out.println(hash);
    }
}