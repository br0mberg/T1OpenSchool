package ru.t1.java.demo.aop;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.t1.java.demo.model.TimeLimitExceedLog;
import ru.t1.java.demo.service.TimeLimitExceedLogService;

@Component
@Aspect
@RequiredArgsConstructor
public class TimeLimitExceedAspect {
    @Value("${method.execution.time.limit}")
    private long timeLimit;

    private final TimeLimitExceedLogService timeLimitExceedLogService;

    @Around("@annotation(ru.t1.java.demo.aop.TimeLimitExceed)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        long executionTime = System.currentTimeMillis() - start;

        if (executionTime > timeLimit) {
            TimeLimitExceedLog log = new TimeLimitExceedLog();
            log.setExecutionTime(executionTime);
            log.setMethodSignature(joinPoint.getSignature().toString());

            timeLimitExceedLogService.save(log);
        }

        return proceed;
    }
}
