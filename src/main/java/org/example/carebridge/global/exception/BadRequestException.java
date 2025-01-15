package org.example.carebridge.global.exception;

public class BadRequestException extends CustomException {

  public BadRequestException(ExceptionType exceptionType) {
    super(exceptionType);
  }
}
