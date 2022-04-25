package ru.irlix.learnit.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(value = NotFoundException.class)
    protected ResponseEntity<ExceptionResponse> handleNotFoundException(RuntimeException ex) {
        ExceptionResponse response = new ExceptionResponse(ex.getMessage());
        log.error(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {NameAlreadyTakenException.class,
            NoRightVariantInQuestionException.class,
            NoRelatedDirectionException.class})
    protected ResponseEntity<ExceptionResponse> handleNameAlreadyTakenException(RuntimeException ex) {
        ExceptionResponse response = new ExceptionResponse(ex.getMessage());
        log.error(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = UnvalidatedJwtException.class)
    protected ResponseEntity<ExceptionResponse> handleUnvalidatedJwtException(RuntimeException ex) {
        ExceptionResponse response = new ExceptionResponse(ex.getMessage());
        log.error(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }
}
