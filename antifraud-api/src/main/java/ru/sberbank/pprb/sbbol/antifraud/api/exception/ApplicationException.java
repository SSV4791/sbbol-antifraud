package ru.sberbank.pprb.sbbol.antifraud.api.exception;

/**
 * Ошибка указывающая, что при отправке на анализ документ не найден в БД
 */
public class ApplicationException extends RuntimeException {

    // конструктор для jsonRpc
    public ApplicationException(String message) {
        super(message);
    }

    public ApplicationException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
