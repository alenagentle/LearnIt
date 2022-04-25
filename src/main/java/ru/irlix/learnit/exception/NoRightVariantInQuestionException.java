package ru.irlix.learnit.exception;

public class NoRightVariantInQuestionException extends RuntimeException {

    public NoRightVariantInQuestionException() {
        super("Impossible to create a question without the correct answer option");
    }
}
