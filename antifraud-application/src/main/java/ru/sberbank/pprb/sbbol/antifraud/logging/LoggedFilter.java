package ru.sberbank.pprb.sbbol.antifraud.logging;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Компонент, добавляющий сквозной идентификатор запроса
 */
@Component
public class LoggedFilter extends GenericFilterBean {

    /**
     * Такое название используется в платформенном логгере для заполнения поля "Запрос" и поиска по нему
     */
    private static final String REQUEST_UID = "requestUid";

    /**
     * Заголовок, содержащий сквозной идентификатор
     */
    private static final String X_REQUEST_ID = "x-request-id";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String xRequestId = ((HttpServletRequest) request).getHeader(X_REQUEST_ID);
        MDC.put(REQUEST_UID, xRequestId);
        try {
            chain.doFilter(request, response);
        } finally {
            MDC.remove(REQUEST_UID);
        }
    }

}
