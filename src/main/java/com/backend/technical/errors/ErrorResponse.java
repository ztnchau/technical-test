package com.backend.technical.errors;

import com.backend.technical.utils.CommonUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ErrorResponse {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = CommonUtils.COMMON_DATE_TIME_PATTERN)
    private final Date timestamp;
    private int code;
    private String status;
    private String message;
    private List<ErrorItem> errors;
    private String stackTrace;


    public ErrorResponse() {
        timestamp = CommonUtils.now();
    }

    public ErrorResponse(final HttpStatus httpStatus, final String message) {
        this();
        this.code = httpStatus.value();
        this.status = httpStatus.name();
        this.message = message;
    }

    public ErrorResponse(final HttpStatus httpStatus, final String message, final String stackTrace) {
        this(httpStatus, message);
        this.stackTrace = stackTrace;
    }

    public ErrorResponse(final HttpStatus httpStatus, final String message , final List<ErrorItem> errors) {
        this(httpStatus, message);
        this.errors = errors;
    }

    public ErrorResponse(final HttpStatus httpStatus, final String message , final List<ErrorItem> errors, final String stackTrace) {
        this(httpStatus, message);
        this.stackTrace = stackTrace;
        this.errors = errors;
    }
}
