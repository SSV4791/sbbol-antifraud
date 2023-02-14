package ru.sberbank.pprb.sbbol.antifraud.api.exception;

/**
 * Не обрабатываемое исключение
 */
public class UnhandledException extends RuntimeException {

    // конструктор для jsonRpc
    public UnhandledException(String message) {
        super(message);
    }

    public UnhandledException(String message, Throwable cause) {
        super(message, cause);
    }

}
