package com.github.kmltml.toylang.parsing;

import com.github.kmltml.toylang.ast.Expression;

/**
 * An error during parsing, signalling a syntax error.
 */
public class ParsingException extends Exception {

    public ParsingException(String message) {
        super(message);
    }

    /**
     * Error signifying, that a token was found, where a token of other type was expected.
     * @param expected The expected token type
     * @param found The actual token, that caused this error.
     */
    public static ParsingException unexpectedToken(Token.Type expected, Token found) {
        return unexpectedToken(expected.toString(), found);
    }

    /**
     * Error signifying, that a token was found, where something different was expected.
     * @param expected Description of the expected element.
     * @param found The actual token, that caused this error.
     */
    public static ParsingException unexpectedToken(String expected, Token found) {
        return new ParsingException("Expected " + expected + ", " + found + " found.");
    }

    /**
     * Error occurring, when the first argument of a lambda expression is not an identifier.
     * @param expr The actual first argument, that caused this error.
     */
    public static ParsingException notAValidLambdaArg(Expression expr) {
        return new ParsingException(String.format("%s is not a valid lambda formal argument", expr));
    }
}
