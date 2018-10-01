package com.martin.logging;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import static com.martin.helper.Ansi.*;

@Aspect
@Component
public class Logging {

    @Before("execution(* com.martin.repository.*.*(..))")
    public void logBefore(JoinPoint point) {
        showInfo(point.getSignature().getName());
    }

    private void showInfo(String str) {
        System.out.print(ANSI_BLUE + "============== Вызов метода " + str + " ==============\n" + ANSI_BLACK);
    }
}