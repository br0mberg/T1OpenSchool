package ru.t1.java.demo.aop;

import org.aspectj.lang.annotation.Aspect;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import ru.t1.java.demo.model.DataSourceErrorLog;
import ru.t1.java.demo.service.DataSourceErrorLogService;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.AssertionErrors.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
class ExceptionLoggingAspectTest {

    @MockBean
    private DataSourceErrorLogService errorLogService;

    @Autowired
    private ExceptionLoggingAspect exceptionLoggingAspect;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        Field field = ExceptionLoggingAspect.class.getDeclaredField("errorLogService");
        field.setAccessible(true);
        field.set(exceptionLoggingAspect, errorLogService);
    }

    @Test
    void testLogException_WhenExceptionIsThrown() {
        Exception exception = new Exception("Test exception");

        System.out.println("Исключение выброшено: " + exception.getMessage());

        exceptionLoggingAspect.logException(exception);

        System.out.println("Исключение перехватил аспект");

        ArgumentCaptor<DataSourceErrorLog> logCaptor = ArgumentCaptor.forClass(DataSourceErrorLog.class);
        verify(errorLogService, times(1)).save(logCaptor.capture());

        DataSourceErrorLog capturedLog = logCaptor.getValue();

        assertEquals("Test exception", capturedLog.getMessage());
        assertNotNull(capturedLog.getStackTrace(), "Stack trace should not be null");

        System.out.println("Лог записан в БД");
    }

}
