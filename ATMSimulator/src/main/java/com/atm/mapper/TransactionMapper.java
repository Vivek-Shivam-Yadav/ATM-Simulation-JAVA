package com.atm.mapper;

import com.atm.model.Transaction;
import com.atm.dto.TransactionDTO;
import com.atm.util.DateTimeUtil;

public class TransactionMapper {

    public static TransactionDTO toDTO(Transaction transaction) {
        if (transaction == null) return null;

        return new TransactionDTO(
            transaction.getTransactionId(),
            transaction.getAccountNumber(),
            transaction.getType(),
            transaction.getAmount(),
            DateTimeUtil.formatDateTime(transaction.getTimestamp()),
            transaction.getTargetAccount(),
            transaction.getStatus()
        );
    }

    public static Transaction toEntity(TransactionDTO dto) {
        if (dto == null) return null;

        return new Transaction(
            dto.getTransactionId(),
            dto.getAccountNumber(),
            dto.getType(),
            dto.getAmount(),
            DateTimeUtil.parseDateTime(dto.getTimestamp()),
            dto.getTargetAccount(),
            dto.getStatus()
        );
    }
}