package ru.t1.java.demo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.t1.java.demo.model.DataSourceErrorLog;
import ru.t1.java.demo.repository.DataSourceErrorLogRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class DataSourceErrorLogService {

    private final DataSourceErrorLogRepository repository;

    @Transactional
    public DataSourceErrorLog save(DataSourceErrorLog dataSourceErrorLog) {
        return repository.save(dataSourceErrorLog);
    }

}
