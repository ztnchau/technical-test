package com.backend.technical.errors;

import com.backend.technical.exceptions.CustomErrorException;
import java.io.PrintWriter;
import java.io.StringWriter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomControllerAdvice {

    @ExceptionHandler(CustomErrorException.class)
    public ResponseEntity<ErrorResponse> handleCustomErrorExceptions(final Exception e) {
        final CustomErrorException customErrorException = (CustomErrorException) e;

        final HttpStatus status = customErrorException.getStatus();

        final StringWriter stringWriter = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(stringWriter);
        customErrorException.printStackTrace(printWriter);
        final String stackTrace = stringWriter.toString();

        final ErrorResponse error = new ErrorResponse(status, customErrorException.getMessage(),
                customErrorException.getData(), stackTrace);

        return new ResponseEntity<>(error, status);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnExceptedExceptions(final Exception e) {

        final HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        final StringWriter stringWriter = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(stringWriter);
        e.printStackTrace(printWriter);
        final String stackTrace = stringWriter.toString();

        final ErrorResponse error = new ErrorResponse(status, e.getMessage(), stackTrace);

        return new ResponseEntity<>(error, status);
    }
}
