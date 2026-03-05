package com.ATM.atm_7smstr.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            // 🔹 Desactivar CSRF (solo para pruebas de API REST)
            .csrf(csrf -> csrf.disable())

            // 🔹 Permitir libre acceso a tu API de autenticación y otros recursos
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll() // endpoints de API
                .requestMatchers("/auth/**", "/css/**", "/js/**").permitAll() // login web y recursos estáticos
                .anyRequest().authenticated() // todo lo demás requiere autenticación
            )

            // 🔹 Desactivar login web por defecto de Spring Security
            .formLogin(form -> form.disable())

            // 🔹 Desactivar HTTP Basic
            .httpBasic(basic -> basic.disable())

            // 🔹 Deshabilitar frameOptions (para H2 o pruebas)
            .headers(headers -> headers.frameOptions(frame -> frame.disable()));

        return http.build();
    }
}