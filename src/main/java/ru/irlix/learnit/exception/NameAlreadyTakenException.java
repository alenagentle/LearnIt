package ru.irlix.learnit.exception;

public class NameAlreadyTakenException extends RuntimeException {

    public NameAlreadyTakenException(String name) {
        super(String.format("Name '%s' already taken", name));
    }
}
