package org.example.carebridge.global.exception;

public class BoardNotFoundException extends CustomException {
    public BoardNotFoundException() {
        super(ExceptionType.BOARD_NOT_FOUND);
    }
}
