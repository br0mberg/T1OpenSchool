package ru.t1.java.demo.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.t1.java.demo.model.Client;
import ru.t1.java.demo.service.impl.AccountServiceImpl;
import ru.t1.java.demo.service.impl.ClientServiceImpl;
import ru.t1.java.demo.service.impl.TransactionServiceImpl;

import java.io.IOException;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class DataInitializerService {

    private final ClientServiceImpl clientService;
    private final AccountServiceImpl accountService;
    private final TransactionServiceImpl transactionService;

    @PostConstruct
    void init() {
        try {
            initializeClients();
            initializeAccounts();
            initializeTransactions();
        } catch (IOException e) {
            log.error("Ошибка во время инициализации данных", e);
        }
    }

    private void initializeClients() throws IOException {
        clientService.init();
        log.info("Клиенты успешно сохранены в базу данных");
    }

    private void initializeAccounts() throws IOException {
        accountService.init();
        log.info("Аккаунты успешно сохранены в базу данных");
    }

    private void initializeTransactions() throws IOException {
        transactionService.init();
        log.info("Транзакции успешно сохранены в базу данных");
    }
}
