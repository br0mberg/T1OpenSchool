package ru.t1.java.demo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.t1.java.demo.model.TimeLimitExceedLog;
import ru.t1.java.demo.repository.TimeLimitExceedLogRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class TimeLimitExceedLogService {

    private final TimeLimitExceedLogRepository repository;

    @Transactional
    public TimeLimitExceedLog save(TimeLimitExceedLog timeLimitExceedLog) {
        return repository.save(timeLimitExceedLog);
    }
}
