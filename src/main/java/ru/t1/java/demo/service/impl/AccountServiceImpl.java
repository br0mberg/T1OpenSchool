package ru.t1.java.demo.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.t1.java.demo.dto.AccountDto;
import ru.t1.java.demo.model.Account;
import ru.t1.java.demo.model.Client;
import ru.t1.java.demo.repository.AccountRepository;
import ru.t1.java.demo.repository.ClientRepository;
import ru.t1.java.demo.service.JsonParseService;
import ru.t1.java.demo.util.AccountMapper;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountServiceImpl implements JsonParseService<Account> {

    private final AccountRepository accountRepository;

    private final ClientRepository clientRepository;

    public void init() {
        try {
            List<Account> accounts = parseJson();
            accountRepository.saveAll(accounts);
            log.info("Клиенты успешно сохранены в базу данных");
        } catch (IOException e) {
            log.error("Ошибка во время обработки записей", e);
        }
    }

    @Transactional
    public Account save(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public List<Account> parseJson() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        AccountDto[] accounts = mapper.readValue(new File("src/main/resources/mock_data/MOCK_DATA_ACCOUNT.json"), AccountDto[].class);

        return Arrays.stream(accounts)
                .map(accountDto -> {
                    Client client = clientRepository.findById(accountDto.getClientId())
                            .orElseThrow(() -> new IllegalArgumentException("Client not found with id: " + accountDto.getClientId()));

                    return AccountMapper.toEntity(accountDto, client);
                })
                .collect(Collectors.toList());
    }
}
