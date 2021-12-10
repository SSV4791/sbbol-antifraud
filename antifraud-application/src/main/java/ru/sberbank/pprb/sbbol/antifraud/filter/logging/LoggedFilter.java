package ru.sberbank.pprb.sbbol.antifraud.filter.logging;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.util.UUID;

/**
 * Компонент, добавляющий сквозной идентификатор запроса
 */
@Component
public class LoggedFilter extends GenericFilterBean {

    /**
     * Такое название используется в платформенном логгере для заполнения поля "Запрос" и поиска по нему
     */
    private static final String REQUEST_UID = "requestUid";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        MDC.put(REQUEST_UID, UUID.randomUUID().toString());
        try {
            chain.doFilter(request, response);
        } finally {
            MDC.remove(REQUEST_UID);
        }
    }

}
