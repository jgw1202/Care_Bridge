package org.example.carebridge.global.exception;

public class PaymentException extends CustomException {
    public PaymentException(ExceptionType exceptionType) {
        super(exceptionType);
    }
}
