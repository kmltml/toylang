package com.github.kmltml.toylang.parsing;

import com.github.kmltml.toylang.ast.Expression;
import com.github.kmltml.toylang.ast.NumberExpression;
import com.github.kmltml.toylang.ast.StringExpression;
import com.github.kmltml.toylang.ast.VarExpression;

public class Parser {

    private Token[] tokens;
    private int cursor;

    public Parser(Token[] tokens) {
        this.tokens = tokens;
        cursor = 0;
    }

    private Token getToken(int i) {
        return i >= tokens.length ? Token.EOF : tokens[i];
    }

    public Token peek() {
        return peek(0);
    }

    public Token peek(int lookahead) {
        return getToken(cursor + lookahead);
    }

    public Token pop() {
        return getToken(cursor++);
    }

    public void consume(Token.Type type) throws ParsingException {
        Token token = pop();
        if (!token.matches(type)) {
            throw ParsingException.unexpectedToken(type, token);
        }
    }

    public Expression parseExpression() throws ParsingException {
        Token token = pop();
        switch (token.getType()) {
            case String:
                return new StringExpression(token);
            case Number:
                return new NumberExpression(token);
            case Identifier:
                return new VarExpression(token);
            default:
                throw ParsingException.unexpectedToken("Expression Start", token);
        }
    }

    public Expression parseExpressionWhole() throws ParsingException {
        Expression expr = parseExpression();
        if (!isAtEnd()) {
            throw ParsingException.unexpectedToken(Token.Type.Eof, peek());
        }
        return expr;
    }

    public boolean isAtEnd() {
        return peek().matches(Token.Type.Eof);
    }
}
