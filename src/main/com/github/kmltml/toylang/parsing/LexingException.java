package com.github.kmltml.toylang.parsing;

/**
 * An error encountered during splitting the source code into tokens.
 */
public class LexingException extends Exception {

    public LexingException(String message) {
        super(message);
    }

    /**
     * Signals that a character was encountered, that cannot start a token
     * @param c The encountered character
     */
    public static LexingException invalidTokenStart(char c) {
        return new LexingException(String.format("%s is not a valid token start", c));
    }

    /**
     * This exception is thrown, when a valid operator prefix is encountered, but it's not complete.
     * @param c The first character of the operator.
     */
    public static LexingException invalidOperator(char c) {
        return new LexingException(String.format("Could not find a valid operator starting with %s", c));
    }
}
