package ru.irlix.learnit.exception;

public class FieldAlreadyTakenException extends RuntimeException {

    public FieldAlreadyTakenException(String value, String field) {
        super(String.format("%s '%s' already taken", value, field));
    }
}
