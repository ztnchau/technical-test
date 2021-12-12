package com.backend.technical.exceptions;

import com.backend.technical.errors.ErrorItem;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class CustomErrorException extends RuntimeException  {
    private HttpStatus status = null;
    private List<ErrorItem> errors = new ArrayList<>();

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

    public CustomErrorException(final HttpStatus status, final String message, final List<ErrorItem> errors) {
        this(status, message);
        this.errors = errors;
    }
}
