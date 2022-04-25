package ru.irlix.learnit.exception;

public class UnvalidatedJwtException extends RuntimeException {

    public UnvalidatedJwtException(String message) {
        super(message);
    }
}
