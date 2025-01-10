package org.example.carebridge.global.exception;

import org.springframework.http.HttpStatus;

public enum ExceptionType {

    USER_NOT_FOUND(HttpStatus.NOT_FOUND,  "해당 유저의 정보를 찾을 수 없습니다."),

    BOARD_NOT_FOUND(HttpStatus.NOT_FOUND,  "해당 보드의 정보를 찾을 수 없습니다."),

    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND,  "해당 댓글의 정보를 찾을 수 없습니다."),

    LIKE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 좋아요의 정보를 찾을 수 없습니다."),

    EXIST_USER(HttpStatus.BAD_REQUEST, "동일한 이메일의 사용자가 존재합니다."),

    DELETED_USER(HttpStatus.BAD_REQUEST, "이미 삭제된 유저입니다."),

    WRONG_PASSWORD(HttpStatus.UNAUTHORIZED, "비밀번호가 잘못되었습니다."),

    FORBIDDEN_ACTION(HttpStatus.FORBIDDEN, "권한이 없습니다."),

    FILE_NOT_FOUND(HttpStatus.NOT_FOUND,"해당 파일의 정보를 찾을 수 없습니다"),

    UNSUPPORTED_FILE_TYPE(HttpStatus.BAD_REQUEST, "허용되지 않는 파일 형식입니다."),

    FILE_SIZE_EXCEEDED(HttpStatus.BAD_REQUEST,"파일 크기가 5MB를 초과할 수 없습니다");



    private final HttpStatus status;
    private final String message;

    ExceptionType(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
