package com.github.kmltml.toylang.parsing;

import com.github.kmltml.toylang.ast.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class responsible for transforming token stream into {@link Expression} trees.
 * Uses a combination of Pratt parsing for infix operators, and recursive descent parsing
 * for everything else.
 */
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

    private Token peek() {
        return peek(0);
    }

    private Token peek(int lookahead) {
        return getToken(cursor + lookahead);
    }

    private Token pop() {
        return getToken(cursor++);
    }

    private Token consume(Token.Type type) throws ParsingException {
        Token token = pop();
        if (!token.matches(type)) {
            throw ParsingException.unexpectedToken(type, token);
        }
        return token;
    }

    /**
     * Parses an expression, that does not contain any infix operator applications
     */
    private Expression parseAtomExpression() throws ParsingException {
        Token token = pop();
        switch (token.getType()) {
            case String:
                return new StringExpression(token);
            case Number:
                return new NumberExpression(token);
            case Identifier:
                if (peek().matches(Token.Type.Arrow)) {
                    pop();
                    Expression body = parseExpression(0);
                    return new LambdaExpression(Collections.singletonList(token.identifierName()), body);
                } else {
                    return new VarExpression(token);
                }
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
                    case Class: {
                        String name = consume(Token.Type.Identifier).identifierName();
                        consume(Token.Type.LBrace);
                        List<Expression> body = parseBlockBody();
                        return new ClassDefExpression(name, body);
                    }
                    case New: {
                        String name = consume(Token.Type.Identifier).identifierName();
                        return new NewExpression(name);
                    }
                    default:
                        throw ParsingException.unexpectedToken("Expression Start", token);
                }
            case LParen:
                // Could be a start of a lambda expression, or just a regular expression wrapped in parentheses
                if (peek().matches(Token.Type.RParen)) {
                    // Lambda without arguments, like `() => 10`
                    pop();
                    consume(Token.Type.Arrow);
                    Expression body = parseExpression(0);
                    return new LambdaExpression(Collections.emptyList(), body);
                }
                Expression expr = parseExpression(0);
                Token t = pop();
                if(t.matches(Token.Type.RParen)) {
                    if (peek().matches(Token.Type.Arrow)) {
                        // Lambda with single argument
                        pop();
                        String arg = ensureVar(expr);
                        Expression body = parseExpression(0);
                        return new LambdaExpression(Collections.singletonList(arg), body);
                    } else {
                        return expr;
                    }
                } else if (t.matches(Token.Type.Comma)) {
                    // Lambda with multiple arguments
                    List<String> args = new ArrayList<>();
                    args.add(ensureVar(expr));
                    while (true) {
                        Token argToken = consume(Token.Type.Identifier);
                        args.add(argToken.identifierName());
                        Token sep = pop();
                        if (sep.matches(Token.Type.RParen)) {
                            break;
                        } else if (!sep.matches(Token.Type.Comma)) {
                            throw ParsingException.unexpectedToken(Token.Type.RParen, sep);
                        }
                    }
                    consume(Token.Type.Arrow);
                    Expression body = parseExpression(0);
                    return new LambdaExpression(args, body);
                } else {
                    throw ParsingException.unexpectedToken(Token.Type.RParen, t);
                }
            case Arrow: {
                Expression body = parseExpression(0);
                return new LambdaExpression(Collections.emptyList(), body);
            }
            case LBrace:
                return new BlockExpression(parseBlockBody());
            case Operator:
                // Prefix operator application
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

    private List<Expression> parseBlockBody() throws ParsingException {
        List<Expression> statements = new ArrayList<>();
        while (!peek().matches(Token.Type.RBrace)) {
            statements.add(parseExpression(0));
            consume(Token.Type.Semicolon);
        }
        consume(Token.Type.RBrace);
        return statements;
    }

    private String ensureVar(Expression expr) throws ParsingException {
        if (expr instanceof VarExpression) {
            return ((VarExpression) expr).getName();
        } else {
            throw ParsingException.notAValidLambdaArg(expr);
        }
    }

    /**
     * Parses an expression.
     * @param precedence The precedence of the surrounding context. Precedence of 0 will parse all expressions.
     * @return The parsed expression.
     * @throws ParsingException Thrown on any syntax error.
     */
    public Expression parseExpression(int precedence) throws ParsingException {
        Expression left = parseAtomExpression();
        while (currentPrecedence() > precedence) {
            Token token = pop();
            if (token.matches(Token.Type.Operator)) {
                InfixOp op = token.infixOpValue();
                if (op == InfixOp.Dot) {
                    Token method = consume(Token.Type.Identifier);
                    left = new MethodExpression(left, method.identifierName());
                } else {
                    Expression right = parseExpression(op.isLeftAssoc() ? op.getPrecedence() : op.getPrecedence() - 1);
                    left = new InfixExpression(op, left, right);
                }
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

    /**
     * Parse an expression, ensuring that the whole input stream gets consumed.
     * @return The parsed expression.
     * @throws ParsingException Thrown on any syntax error.
     */
    public Expression parseExpressionWhole() throws ParsingException {
        Expression expr = parseExpression(0);
        if (!isAtEnd()) {
            throw ParsingException.unexpectedToken(Token.Type.Eof, peek());
        }
        return expr;
    }

    /**
     * Checks, if all input has been consumed.
     * @return true, if all tokens have been consumed.
     */
    public boolean isAtEnd() {
        return peek().matches(Token.Type.Eof);
    }
}
