package com.sgsatm.transaction.service;

import com.sgsatm.transaction.dto.TransactionRequestDTO;

import com.sgsatm.transaction.entity.Transaction;

public interface TransactionService {
    Transaction createTransaction(TransactionRequestDTO request);
}