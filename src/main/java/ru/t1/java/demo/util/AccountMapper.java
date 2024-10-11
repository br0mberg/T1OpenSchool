package ru.t1.java.demo.util;

import org.springframework.stereotype.Component;
import ru.t1.java.demo.dto.AccountDto;
import ru.t1.java.demo.model.Account;
import ru.t1.java.demo.model.Client;

@Component
public class AccountMapper {
    public static Account toEntity(AccountDto accountDto, Client client) {
        if (accountDto == null) {
            return null;
        }
        return Account.builder()
                .balance(accountDto.getBalance())
                .client(client)
                .type(accountDto.getType())
                .build();
    }

    public static AccountDto toDto(Account account) {
        if (account == null) {
            return null;
        }

        return AccountDto.builder()
                .id(account.getId())
                .type(account.getType())
                .balance(account.getBalance())
                .clientId(account.getClient() != null ? account.getClient().getId() : null)
                .build();
    }

}