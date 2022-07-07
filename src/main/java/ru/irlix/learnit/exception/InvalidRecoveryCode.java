package ru.irlix.learnit.exception;

public class InvalidRecoveryCode extends RuntimeException {

    public InvalidRecoveryCode(String message) {
        super(message);
    }
}
