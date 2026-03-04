package com.sgsatm.transaction.service;

import com.sgsatm.transaction.dto.TransactionRequestDTO;
import com.sgsatm.transaction.entity.Transaction;
import com.sgsatm.transaction.repository.TransactionRepository;

import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository repository;

    public TransactionServiceImpl(TransactionRepository repository) {
        this.repository = repository;
    }

    @Override
    public Transaction createTransaction(TransactionRequestDTO request) {

        Transaction transaction = new Transaction();
        transaction.setAmount(request.getAmount());
        transaction.setType(request.getType());

        return repository.save(transaction);
    }
}