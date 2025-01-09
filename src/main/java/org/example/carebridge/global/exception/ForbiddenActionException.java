package org.example.carebridge.global.exception;

public class ForbiddenActionException extends CustomException {
    public ForbiddenActionException() {
        super(ExceptionType.FORBIDDEN_ACTION);
    }
}
