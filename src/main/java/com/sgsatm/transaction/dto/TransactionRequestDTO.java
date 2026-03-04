package com.sgsatm.transaction.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class TransactionRequestDTO {

    private Long accountId;
    private String type;
    private BigDecimal amount;
}