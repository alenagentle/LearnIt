package ru.irlix.learnit.exception;

public class NoRelatedDirectionException extends RuntimeException {

    public NoRelatedDirectionException() {
        super("Cant create topic without related direction");
    }
}
