package org.example.carebridge.global.exception;

import lombok.Getter;

@Getter
public abstract class BadValueException extends CustomException {
    public BadValueException(final ExceptionType exceptionType) {
        super(exceptionType);
    }
}
