package com.ATM.atm_7smstr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ATM.atm_7smstr.entity.SecurityLog;

public interface SecurityLogRepository extends JpaRepository<SecurityLog, Long> {

}