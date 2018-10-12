package com.github.kmltml.toylang.parsing;

import com.github.kmltml.toylang.ast.*;

import java.util.ArrayList;
import java.util.List;

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

    public Token consume(Token.Type type) throws ParsingException {
        Token token = pop();
        if (!token.matches(type)) {
            throw ParsingException.unexpectedToken(type, token);
        }
        return token;
    }

    public Expression parseAtomExpression() throws ParsingException {
        Token token = pop();
        switch (token.getType()) {
            case String:
                return new StringExpression(token);
            case Number:
                return new NumberExpression(token);
            case Identifier:
                return new VarExpression(token);
            case Keyword:
                switch (token.keywordValue()) {
                    case True:
                    case False:
                        return new BoolExpression(token);
                    case If: {
                        consume(Token.Type.LParen);
                        Expression cond = parseExpression(0);
                        consume(Token.Type.RParen);
                        Expression ifTrue = parseExpression(0);
                        Expression ifFalse = null;
                        if (peek().matchesKeyword(Keyword.Else)) {
                            pop();
                            ifFalse = parseExpression(0);
                        }
                        return new IfExpression(cond, ifTrue, ifFalse);
                    }
                    case Var: {
                        Token name = consume(Token.Type.Identifier);
                        Token op = consume(Token.Type.Operator);
                        if (op.infixOpValue() != InfixOp.Assign) {
                            throw ParsingException.unexpectedToken("=", op);
                        }
                        Expression right = parseExpression(0);
                        return new VarDefExpression(name.identifierName(), right);
                    }
                    case While: {
                        consume(Token.Type.LParen);
                        Expression cond = parseExpression(0);
                        consume(Token.Type.RParen);
                        Expression body = parseExpression(0);
                        return new WhileExpression(cond, body);
                    }
                    default:
                        throw ParsingException.unexpectedToken("Expression Start", token);
                }
            case LParen:
                Expression expr = parseExpression(0);
                consume(Token.Type.RParen);
                return expr;
            case LBrace:
                List<Expression> statements = new ArrayList<>();
                while (!peek().matches(Token.Type.RBrace)) {
                    statements.add(parseExpression(0));
                    consume(Token.Type.Semicolon);
                }
                consume(Token.Type.RBrace);
                return new BlockExpression(statements);
            case Operator:
                if (PrefixOp.isPrefixOp(token.getSource())) {
                    PrefixOp op = token.prefixOpValue();
                    return new PrefixExpression(op, parseExpression(op.getPrecedence()));
                } else {
                    throw ParsingException.unexpectedToken("Expression Start", token);
                }
            default:
                throw ParsingException.unexpectedToken("Expression Start", token);
        }
    }

    public Expression parseExpression(int precedence) throws ParsingException {
        Expression left = parseAtomExpression();
        while (currentPrecedence() > precedence) {
            Token token = pop();
            if (token.matches(Token.Type.Operator)) {
                InfixOp op = token.infixOpValue();
                Expression right = parseExpression(op.isLeftAssoc() ? op.getPrecedence() : op.getPrecedence() - 1);
                left = new InfixExpression(op, left, right);
            } else if (token.matches(Token.Type.LParen)) {
                List<Expression> args = new ArrayList<>();
                if (peek().matches(Token.Type.RParen)) {
                    pop();
                } else {
                    while (true) {
                        args.add(parseExpression(0));
                        Token t = pop();
                        if (t.matches(Token.Type.RParen)) {
                            break;
                        } else if (!t.matches(Token.Type.Comma)) {
                            throw ParsingException.unexpectedToken(Token.Type.RParen, t);
                        }
                    }
                }
                left = new CallExpression(left, args);
            } else {
                throw ParsingException.unexpectedToken("Expression postfix", token);
            }
        }
        return left;
    }

    private int currentPrecedence() {
        Token token = peek();
        if (token.matches(Token.Type.Operator)) {
            return token.infixOpValue().getPrecedence();
        } else if(token.matches(Token.Type.LParen)) {
            return 10;
        } else {
            return 0;
        }
    }

    public Expression parseExpressionWhole() throws ParsingException {
        Expression expr = parseExpression(0);
        if (!isAtEnd()) {
            throw ParsingException.unexpectedToken(Token.Type.Eof, peek());
        }
        return expr;
    }

    public boolean isAtEnd() {
        return peek().matches(Token.Type.Eof);
    }
}
