package ru.t1.java.demo.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.util.ReflectionTestUtils;
import ru.t1.java.demo.model.TimeLimitExceedLog;
import ru.t1.java.demo.service.TimeLimitExceedLogService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.yml")
class TimeLimitExceedAspectTest {

    @Mock
    private TimeLimitExceedLogService timeLimitExceedLogService;

    @InjectMocks
    private TimeLimitExceedAspect timeLimitExceedAspect;

    @Value("${method.execution.time.limit}")
    private long timeLimit;

    @BeforeEach
    public void setup() {
        ReflectionTestUtils.setField(timeLimitExceedAspect, "timeLimit", timeLimit);
    }

    @Test
    void testLogExecutionTime_WhenExecutionExceedsLimit() throws Throwable {
        ProceedingJoinPoint joinPoint = mock(ProceedingJoinPoint.class);
        when(joinPoint.proceed()).thenAnswer(invocation -> {
            Thread.sleep(1500);
            return null;
        });

        Signature signature = mock(Signature.class);
        when(signature.toString()).thenReturn("mockedMethodSignature");
        when(joinPoint.getSignature()).thenReturn(signature);

        System.out.println("Запускаем метод, который превышает лимит времени...");

        timeLimitExceedAspect.logExecutionTime(joinPoint);

        ArgumentCaptor<TimeLimitExceedLog> logCaptor = ArgumentCaptor.forClass(TimeLimitExceedLog.class);
        verify(timeLimitExceedLogService, times(1)).save(logCaptor.capture());

        TimeLimitExceedLog capturedLog = logCaptor.getValue();
        assertTrue(capturedLog.getExecutionTime() >= 1500, "Execution time should be at least 1500ms");
        assertEquals(joinPoint.getSignature().toString(), capturedLog.getMethodSignature());

        System.out.println("Лог превышения времени выполнен для метода: " + capturedLog.getMethodSignature());
    }

    @Test
    void testLogExecutionTime_WhenExecutionDoesNotExceedLimit() throws Throwable {
        ProceedingJoinPoint joinPoint = mock(ProceedingJoinPoint.class);

        Signature signature = mock(Signature.class);
        when(signature.toString()).thenReturn("mockedMethodSignature");

        when(joinPoint.getSignature()).thenReturn(signature);

        System.out.println("Запускаем метод, который не превышает лимит времени...");

        timeLimitExceedAspect.logExecutionTime(joinPoint);

        verify(timeLimitExceedLogService, never()).save(any());

        System.out.println("Лог превышения времени не был записан, так как лимит времени не превышен.");
    }
}
