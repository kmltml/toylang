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
            do {
                cursor++;
                if(peek() == '\\') {
                    cursor += 2;
                }
            } while (peek() != '"');
        } else {
            throw LexingException.invalidTokenStart(c);
        }
        cursor++;
        return new Token(source.substring(start, cursor), type);
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
