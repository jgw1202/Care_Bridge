package org.example.carebridge.global.exception;

public class CommentNotFoundException extends CustomException {
    public CommentNotFoundException() {
        super(ExceptionType.COMMENT_NOT_FOUND);
    }
}