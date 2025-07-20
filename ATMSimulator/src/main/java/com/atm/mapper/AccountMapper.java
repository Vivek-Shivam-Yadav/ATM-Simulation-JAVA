package com.atm.mapper;

import com.atm.model.Account;
import com.atm.dto.AccountDTO;
import com.atm.util.DateTimeUtil;

public class AccountMapper {

    public static AccountDTO toDTO(Account account) {
        if (account == null) return null;

        return new AccountDTO(
            account.getAccountNumber(),
            account.getUserId(),
            account.getBalance(),
            account.getLastTransactionAt() != null ? 
                DateTimeUtil.formatDateTime(account.getLastTransactionAt()) : null
        );
    }

    public static Account toEntity(AccountDTO dto) {
        if (dto == null) return null;

        Account account = new Account(
            dto.getAccountNumber(),
            dto.getUserId(),
            dto.getBalance()
        );

        if (dto.getLastTransactionAt() != null) {
            account.setLastTransactionAt(DateTimeUtil.parseDateTime(dto.getLastTransactionAt()));
        }

        return account;
    }
}