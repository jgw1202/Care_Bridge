package org.example.carebridge.global.exception;

import lombok.Getter;

@Getter
public class WrongAccessException extends CustomException {

     public WrongAccessException(final ExceptionType exceptionType) {
         super(exceptionType);
    }

}
