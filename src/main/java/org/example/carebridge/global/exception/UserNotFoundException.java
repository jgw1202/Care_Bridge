package org.example.carebridge.global.exception;

public class UserNotFoundException extends CustomException {
    public UserNotFoundException() {
        super(ExceptionType.USER_NOT_FOUND);
    }
}
