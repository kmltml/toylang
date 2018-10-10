package com.github.kmltml.toylang.parsing;

public class LexingException extends Exception {

    public LexingException(String message) {
        super(message);
    }

    public static LexingException invalidTokenStart(char c) {
        return new LexingException(String.format("%s is not a valid token start", c));
    }

    public static LexingException invalidOperator(char c) {
        return new LexingException(String.format("Could not find a valid operator starting with %s", c));
    }
}
