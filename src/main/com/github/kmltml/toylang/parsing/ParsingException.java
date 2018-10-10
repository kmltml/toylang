package com.github.kmltml.toylang.parsing;

public class ParsingException extends Exception {

    public ParsingException(String message) {
        super(message);
    }

    public static ParsingException unexpectedToken(Token.Type expected, Token found) {
        return unexpectedToken(expected.toString(), found);
    }

    public static ParsingException unexpectedToken(String expected, Token found) {
        return new ParsingException("Expected " + expected + ", " + found + " found.");
    }
}
