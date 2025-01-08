package org.example.carebridge.global.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Getter
public class ExceptionResponseBody {

    private final HttpStatus httpStatus;
    private final int statusCode;
    private final Map<String, String> error;

    public ExceptionResponseBody(HttpStatus httpStatus, int statusCode, Map<String, String> error) {
        this.httpStatus = httpStatus;
        this.statusCode = statusCode;
        this.error = error;
    }
}
