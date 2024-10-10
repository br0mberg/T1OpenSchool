package ru.t1.java.demo.aop;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.t1.java.demo.model.DataSourceErrorLog;
import ru.t1.java.demo.repository.DataSourceErrorLogRepository;

@Component
@Aspect
public class ExceptionLoggingAspect {

    @Autowired
    private DataSourceErrorLogRepository errorLogRepository;

    @Pointcut("execution(* ru.t1.java.demo.*.*(..))")
    public void applicationMethods() {}

    @AfterThrowing(pointcut = "applicationMethods()", throwing = "ex")
    public void logException(Exception ex) {
        DataSourceErrorLog errorLog = new DataSourceErrorLog();
        errorLog.setStackTrace(getStackTraceAsString(ex));
        errorLog.setMessage(ex.getMessage());
        errorLog.setMethodSignature(ex.getStackTrace()[0].toString());

        errorLogRepository.save(errorLog);
    }

    private String getStackTraceAsString(Exception ex) {
        StringBuilder sb = new StringBuilder();
        for (StackTraceElement element : ex.getStackTrace()) {
            sb.append(element.toString()).append("\n");
        }
        return sb.toString();
    }
}
