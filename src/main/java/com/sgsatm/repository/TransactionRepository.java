package com.sgsatm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sgsatm.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}