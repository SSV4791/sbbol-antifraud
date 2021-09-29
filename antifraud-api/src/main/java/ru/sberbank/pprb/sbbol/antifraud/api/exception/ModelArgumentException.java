package ru.sberbank.pprb.sbbol.antifraud.api.exception;

/**
 * Ошибка валидации модели
 */
public class ModelArgumentException extends RuntimeException {

    // конструктор для jsonRpc
    public ModelArgumentException(String message) {
        super(message);
    }

    public ModelArgumentException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
