package ru.sberbank.pprb.sbbol.antifraud.api.exception;

/**
 * Ошибки в случае неуспешного ответа от ФП ИС
 */
public class AnalyzeException extends RuntimeException {

    // конструктор для jsonRpc
    public AnalyzeException(String message) {
        super(message);
    }

    public AnalyzeException(String message, Throwable cause) {
        super(message, cause);
    }

}
