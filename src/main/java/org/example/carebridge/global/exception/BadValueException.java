package org.example.carebridge.global.exception;

import lombok.Getter;

@Getter
public class BadValueException extends CustomException {
    public BadValueException(final ExceptionType exceptionType) {
        super(exceptionType);
    }
}
