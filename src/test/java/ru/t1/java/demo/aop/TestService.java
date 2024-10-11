package ru.t1.java.demo.aop;

import org.springframework.stereotype.Service;

@Service
public class TestService {
    public void throwException() {
        throw new RuntimeException("Test exception");
    }
}
