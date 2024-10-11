package ru.t1.java.demo.util;

import org.springframework.stereotype.Component;
import ru.t1.java.demo.dto.TransactionDto;
import ru.t1.java.demo.model.Account;
import ru.t1.java.demo.model.Client;
import ru.t1.java.demo.model.Transaction;

@Component
public class TransactionMapper {
    public static Transaction toEntity(TransactionDto transactionDto, Client client, Account account) {
        if (transactionDto == null) {
            return null;
        }
        return Transaction.builder()
                .client(client)
                .account(account)
                .amount(transactionDto.getAmount())
                .build();
    }

    public static TransactionDto toDto(Transaction transaction) {
        if (transaction == null) {
            return null;
        }

        return TransactionDto.builder()
                .id(transaction.getId())
                .accountId(transaction.getId())
                .amount(transaction.getAmount())
                .clientId(transaction.getClient() != null ? transaction.getClient().getId() : null)
                .build();
    }
}
