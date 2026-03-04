package com.sgsatm.service;

import org.springframework.stereotype.Service;

import com.sgsatm.entity.Transaction;
import com.sgsatm.repository.TransactionRepository;

@Service
public class TransactionService {

    private final TransactionRepository repository;

    public TransactionService(TransactionRepository repository) {
        this.repository = repository;
    }

    public Transaction save(Transaction transaction) {
        return repository.save(transaction);
    }
}