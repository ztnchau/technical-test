package com.backend.technical.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class CustomErrorException extends RuntimeException  {
    private HttpStatus status = null;
    private Object data = null;

    public CustomErrorException() {
        super();
    }

    public CustomErrorException(final String message) {
        super(message);
    }

    public CustomErrorException(final HttpStatus status, final String message) {
        this(message);
        this.status = status;
    }

    public CustomErrorException(final HttpStatus status, final String message, final Object data) {
        this(status, message);
        this.data = data;
    }
}
