package com.ATM.atm_7smstr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ATM.atm_7smstr.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    // Opcional: agregar métodos personalizados si necesitas buscar por otro campo
    // Example: Optional<Account> findByUsuarioId(Long usuarioId);
}