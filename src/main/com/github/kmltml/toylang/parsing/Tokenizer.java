package com.github.kmltml.toylang.parsing;

import java.util.*;

/**
 * Class responsible for lexing the source, that is splitting it tokens.
 * This takes care of ignoring whitespace.
 */
public class Tokenizer {

    private String source;
    private int cursor;

    private static Map<Character, Token.Type> singleCharTokens = new HashMap<>();
    static {
        singleCharTokens.put('(', Token.Type.LParen);
        singleCharTokens.put(')', Token.Type.RParen);
        singleCharTokens.put('{', Token.Type.LBrace);
        singleCharTokens.put('}', Token.Type.RBrace);
        singleCharTokens.put(';', Token.Type.Semicolon);
        singleCharTokens.put(',', Token.Type.Comma);
    }

    private static Set<String> operatorPrefixes = new HashSet<>();
    private static Set<String> operatorNames = new HashSet<>();
    static {
        for (InfixOp op : InfixOp.values()) {
            addOperator(op.getName());
        }
        for (PrefixOp op : PrefixOp.values()) {
            addOperator(op.getName());
        }
        addOperator("=>");
    }

    private static void addOperator(String name) {
        operatorNames.add(name);
        for (int i = 1; i <= name.length(); i++) {
            operatorPrefixes.add(name.substring(0, i));
        }
    }

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
        cursor++;
        Token.Type type;
        if (c == '"') {
            type = Token.Type.String;
            while (peek() != '"') {
                if(peek() == '\\') {
                    cursor++;
                }
                cursor++;
            }
            cursor++;
        } else if (isDigit(c)) {
            type = Token.Type.Number;
            boolean dotEncountered = false;
            while (!isAtEnd() && (isDigit(peek()) || !dotEncountered && peek() == '.')) {
                if (peek() == '.') {
                    dotEncountered = true;
                }
                cursor++;
            }
            if (cursor != 0 && source.charAt(cursor - 1) == '.') {
                cursor--;
            }
        } else if (Character.isJavaIdentifierStart(c)) {
            while (!isAtEnd() && Character.isJavaIdentifierPart(peek())) {
                cursor++;
            }
            if (Keyword.isKeyword(source.substring(start, cursor))) {
                type = Token.Type.Keyword;
            } else {
                type = Token.Type.Identifier;
            }
        } else if (operatorPrefixes.contains(String.valueOf(c))) {
            type = Token.Type.Operator;
            while (!isAtEnd() && operatorPrefixes.contains(source.substring(start, cursor))) {
                cursor++;
            }
            while (cursor >= start && !operatorNames.contains(source.substring(start, cursor))) {
                cursor--;
            }
            if (cursor == start) {
                throw LexingException.invalidOperator(c);
            }
            if("=>".equals(source.substring(start, cursor))) {
                type = Token.Type.Arrow;
            }
        } else if (singleCharTokens.containsKey(c)) {
            type = singleCharTokens.get(c);
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
