package ru.irlix.learnit.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.impl.SizeException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.irlix.learnit.exception.ExceptionResponse;
import ru.irlix.learnit.exception.FieldAlreadyTakenException;
import ru.irlix.learnit.exception.FileException;
import ru.irlix.learnit.exception.IncorrectCredentialsException;
import ru.irlix.learnit.exception.InvalidRecoveryCode;
import ru.irlix.learnit.exception.NoRelatedDirectionException;
import ru.irlix.learnit.exception.NoRightVariantInQuestionException;
import ru.irlix.learnit.exception.NotFoundException;
import ru.irlix.learnit.exception.UnvalidatedJwtException;

import javax.mail.MessagingException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<ExceptionResponse> handleNotFoundException(RuntimeException ex) {
        ExceptionResponse response = new ExceptionResponse(ex.getMessage());
        log.error(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PropertyReferenceException.class)
    protected ResponseEntity<ExceptionResponse> handlePropertyReferenceException(PropertyReferenceException ex) {
        String message = String.format("Field with name '%s' not found while sorting", ex.getPropertyName());
        log.error(message);
        ExceptionResponse response = new ExceptionResponse(message);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({FieldAlreadyTakenException.class,
            NoRightVariantInQuestionException.class,
            NoRelatedDirectionException.class,
            IncorrectCredentialsException.class,
            FileException.class})
    protected ResponseEntity<ExceptionResponse> handleNameAlreadyTakenException(RuntimeException ex) {
        ExceptionResponse response = new ExceptionResponse(ex.getMessage());
        log.error(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        String message = ex.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", "));
        ExceptionResponse response = new ExceptionResponse(message);
        log.error(message);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<ExceptionResponse> handleConstraintViolationException(ConstraintViolationException ex) {
        String message = ex.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(", "));
        ExceptionResponse response = new ExceptionResponse(message);
        log.error(message);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnvalidatedJwtException.class)
    protected ResponseEntity<ExceptionResponse> handleUnvalidatedJwtException(RuntimeException ex) {
        ExceptionResponse response = new ExceptionResponse(ex.getMessage());
        log.error(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<ExceptionResponse> handleAccessDeniedException(AccessDeniedException ex) {
        String message = "Forbidden";
        ExceptionResponse response = new ExceptionResponse(message);
        log.error(message);
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(SizeException.class)
    protected ResponseEntity<ExceptionResponse> handleSizeException(SizeException ex) {
        String message = ex.getMessage();
        ExceptionResponse response = new ExceptionResponse(message);
        log.error(message);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<ExceptionResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        String message = ex.getMessage();
        ExceptionResponse response = new ExceptionResponse(message);
        log.error(message);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MessagingException.class)
    protected ResponseEntity<ExceptionResponse> handleIMessagingException(MessagingException ex) {
        String message = ex.getMessage();
        ExceptionResponse response = new ExceptionResponse(message);
        log.error(message);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidRecoveryCode.class)
    protected ResponseEntity<ExceptionResponse> handleInvalidRecoveryCode(InvalidRecoveryCode ex) {
        String message = ex.getMessage();
        ExceptionResponse response = new ExceptionResponse(message);
        log.error(message);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
