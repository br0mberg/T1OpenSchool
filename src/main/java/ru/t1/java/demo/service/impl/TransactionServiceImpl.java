package ru.t1.java.demo.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.t1.java.demo.dto.TransactionDto;
import ru.t1.java.demo.model.Account;
import ru.t1.java.demo.model.Client;
import ru.t1.java.demo.model.Transaction;
import ru.t1.java.demo.repository.AccountRepository;
import ru.t1.java.demo.repository.ClientRepository;
import ru.t1.java.demo.repository.TransactionRepository;
import ru.t1.java.demo.service.JsonParseService;
import ru.t1.java.demo.util.TransactionMapper;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransactionServiceImpl implements JsonParseService<Transaction> {

    private final ClientRepository clientRepository;

    private final AccountRepository accountRepository;

    private final TransactionRepository transactionRepository;

    @PostConstruct
    void init() {
        try {
            List<Transaction> transactions = parseJson();
            transactionRepository.saveAll(transactions);
            log.info("Клиенты успешно сохранены в базу данных");
        } catch (IOException e) {
            log.error("Ошибка во время обработки записей", e);
        }
    }

    @Transactional
    public Transaction save(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Override
    public List<Transaction> parseJson() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        TransactionDto[] transactions = mapper.readValue(new File("src/main/resources/mock_data/MOCK_DATA_TRANSACTION.json"), TransactionDto[].class);

        return Arrays.stream(transactions)
                .map(transactionDto -> {
                    Account account = accountRepository.findById(transactionDto.getAccountId()).orElseThrow(() -> new IllegalArgumentException("Account not found with id: " + transactionDto.getAccountId()));
                    Client client = clientRepository.findById(transactionDto.getClientId()).orElseThrow(()-> new IllegalArgumentException("Client not found with id: " + transactionDto.getClientId()));
                    return TransactionMapper.toEntity(transactionDto,  client, account);
                })
                .collect(Collectors.toList());
    }
}
