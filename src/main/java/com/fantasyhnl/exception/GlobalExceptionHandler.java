package com.fantasyhnl.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    protected ResponseEntity<Object> handleException(Exception exception, HttpStatus status) {
        logger.error(exception.getMessage(), exception);
        if (status != null) ResponseEntity.status(status).body(exception.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
    }

    @ExceptionHandler({InvalidIdException.class})
    protected ResponseEntity<Object> handleInvalidServerApiCallException(InvalidIdException exception) {
        var status = HttpStatus.NOT_FOUND;
        return handleException(exception, status);
    }

    @ExceptionHandler({InvalidServerApiCallException.class})
    protected ResponseEntity<Object> handleInvalidServerApiCallException(InvalidServerApiCallException exception) {
        return handleException(exception, null);
    }

    @ExceptionHandler({InvalidJsonException.class})
    protected ResponseEntity<Object> handleInvalidJsonException(InvalidJsonException exception) {
        return handleException(exception, null);
    }

    @ExceptionHandler({EmptyListException.class})
    protected ResponseEntity<Object> handleTeamListEmptyException(EmptyListException exception) {
        return handleException(exception, null);
//        logger.error(exception.getMessage(), exception);
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
    }

    @ExceptionHandler({RuntimeException.class})
    protected ResponseEntity<Object> handleRuntimeException(RuntimeException exception) {
        return handleException(exception, null);
    }
}
