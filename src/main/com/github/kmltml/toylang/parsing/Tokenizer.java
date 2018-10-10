package com.github.kmltml.toylang.parsing;

import java.util.ArrayList;
import java.util.List;

public class Tokenizer {

    private String source;
    private int cursor;

    public Tokenizer(String source) {
        this.source = source;
    }

    public Token[] tokenize() throws LexingException {
        List<Token> ret = new ArrayList<>();
        Token token;
        do {
            token = readToken();
            ret.add(token);
        } while (token != Token.EOF);
        return ret.toArray(new Token[0]);
    }

    private Token readToken() throws LexingException {
        skipWhitespace();
        if (cursor >= source.length()) {
            return Token.EOF;
        }
        final int start = cursor;
        char c = peek();
        Token.Type type;
        if (c == '"') {
            type = Token.Type.String;
            cursor++;
            while (peek() != '"') {
                if(peek() == '\\') {
                    cursor++;
                }
                cursor++;
            }
            cursor++;
        } else if(isDigit(c)) {
            type = Token.Type.Number;
            boolean dotEncountered = false;
            while (!isAtEnd() && (isDigit(peek()) || !dotEncountered && peek() == '.')) {
                if (peek() == '.') {
                    dotEncountered = true;
                }
                cursor++;
            }
        } else {
            throw LexingException.invalidTokenStart(c);
        }
        return new Token(source.substring(start, cursor), type);
    }

    private boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    private boolean isAtEnd() {
        return cursor >= source.length();
    }

    private char peek() {
        return source.charAt(cursor);
    }

    private void skipWhitespace() {
        while(cursor < source.length() && Character.isWhitespace(peek())) {
            cursor++;
        }
    }

}
