package ru.sberbank.pprb.sbbol.antifraud.exception;

/**
 * Обработанные приложением ошибки, возвращаемые из API
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
