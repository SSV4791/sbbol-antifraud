package ru.sberbank.pprb.sbbol.antifraud.service.aspect.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Аспект, обрабатывающий вызов методов с логированием, отмеченных аннотацией {@link Logged}
 */
@Aspect
@Component
public final class LoggedAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggedAspect.class);

    @Around("within(ru.sberbank.pprb.sbbol.antifraud..*) && @annotation(logged)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint, Logged logged) throws Throwable {
        logInvocation(logged, joinPoint.getSignature(), joinPoint.getArgs());
        Object result = joinPoint.proceed();
        logResult(logged, joinPoint.getSignature(), result);
        return result;
    }

    @AfterThrowing(pointcut = "within(ru.sberbank.pprb.sbbol.antifraud.service.rpc..*) && @annotation(ru.sberbank.pprb.sbbol.antifraud.service.aspect.logging.Logged)", throwing = "ex")
    public void logError(Exception ex) {
        logger.error("Error: ", ex);
    }

    private void logInvocation(Logged logged, Signature signature, Object[] args) {
        if (logged.printRequestResponse() && args.length != 0) {
            logger.debug("Invoking method {} with params {}", signature, new LazyArrayPrinter(args));
        }
    }

    private void logResult(Logged logged, Signature signature, Object arg) {
        if (logged.printRequestResponse()) {
            logger.debug("Method {} invocation result is {}", signature, arg);
        }
    }

    /**
     * Обертка над массивом для отложенного вызова метода {@link Arrays#toString}
     */
    private static final class LazyArrayPrinter {
        private final Object[] args;

        private LazyArrayPrinter(Object[] args) {
            this.args = args;
        }

        @Override
        public String toString() {
            return Arrays.toString(args);
        }
    }

}
