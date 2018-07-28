package com.martin.logging;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import static com.martin.helper.Ansi.*;

@Aspect
@Component
public class Logging {

    private final String WORKING_COLOR = ANSI_BLUE;
    private final String RESET_COLOR = ANSI_RESET;

    @Before("execution(* com.martin.dao.LibraryJDBCdao.*(..))")
    public void logBefore(JoinPoint point) {
        System.out.printf(WORKING_COLOR + "============== Вызов метода %s==============\n" + ANSI_RESET, point.getSignature().getName());
    }
}
