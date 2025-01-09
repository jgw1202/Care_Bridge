package org.example.carebridge.global.exception;

import lombok.Getter;

@Getter
public class NotFoundException extends CustomException {

    public NotFoundException(final ExceptionType exceptionType) {
        super(exceptionType);
    }

}
