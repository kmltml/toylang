package com.github.kmltml.toylang.parsing;

import com.github.kmltml.toylang.ast.*;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class ParserTest {

    @Test
    public void parseExpression_stringLiteral() throws Exception {
        Parser parser = new Parser(new Token[]{
                new Token("\"Hello\"", Token.Type.String),
                Token.EOF
        });
        assertEquals(new StringExpression("Hello"), parser.parseExpressionWhole());
    }

    @Test
    public void parseExpression_intLiteral() throws Exception {
        Parser parser = new Parser(new Token[]{
                new Token("42", Token.Type.Number),
                Token.EOF
        });
        assertEquals(new NumberExpression(new BigDecimal(42)), parser.parseExpressionWhole());
    }

    @Test
    public void parseExpression_floatLiteral() throws Exception {
        Parser parser = new Parser(new Token[]{
                new Token("0.42", Token.Type.Number),
                Token.EOF
        });
        assertEquals(new NumberExpression(new BigDecimal("0.42")), parser.parseExpressionWhole());
    }

    @Test
    public void parseExpression_variableAccess() throws Exception {
        Parser parser = new Parser(new Token[]{
                new Token("foo", Token.Type.Identifier),
                Token.EOF
        });
        assertEquals(new VarExpression("foo"), parser.parseExpressionWhole());
    }

    @Test
    public void parseExpression_booleanLiteralTrue() throws Exception {
        Parser parser = new Parser(new Token[]{
                new Token("true", Token.Type.Keyword),
                Token.EOF
        });
        assertEquals(new BoolExpression(true), parser.parseExpressionWhole());
    }

    @Test
    public void parseExpression_booleanLiteralFalse() throws Exception {
        Parser parser = new Parser(new Token[]{
                new Token("false", Token.Type.Keyword),
                Token.EOF
        });
        assertEquals(new BoolExpression(false), parser.parseExpressionWhole());
    }

    @Test
    public void parseExpression_simpleInfix() throws Exception {
        Parser parser = new Parser(new Token[]{
                new Token("1", Token.Type.Number),
                new Token("+", Token.Type.Operator),
                new Token("2", Token.Type.Number),
                Token.EOF
        });
        assertEquals(
                new InfixExpression(InfixOp.Plus, new NumberExpression(BigDecimal.valueOf(1)), new NumberExpression(BigDecimal.valueOf(2))),
                parser.parseExpressionWhole());
    }

    @Test
    public void parseExpression_infixl() throws Exception {
        Parser parser = new Parser(new Token[]{
                new Token("1", Token.Type.Number),
                new Token("+", Token.Type.Operator),
                new Token("2", Token.Type.Number),
                new Token("+", Token.Type.Operator),
                new Token("3", Token.Type.Number),
                Token.EOF
        });
        assertEquals(
                new InfixExpression(InfixOp.Plus,
                        new InfixExpression(InfixOp.Plus, new NumberExpression(BigDecimal.valueOf(1)), new NumberExpression(BigDecimal.valueOf(2))),
                        new NumberExpression(BigDecimal.valueOf(3))),
                parser.parseExpressionWhole());
    }

    @Test
    public void parseExpression_infixr() throws Exception {
        Parser parser = new Parser(new Token[]{
                new Token("1", Token.Type.Number),
                new Token("**", Token.Type.Operator),
                new Token("2", Token.Type.Number),
                new Token("**", Token.Type.Operator),
                new Token("3", Token.Type.Number),
                Token.EOF
        });
        assertEquals(
                new InfixExpression(InfixOp.Exp,
                        new NumberExpression(BigDecimal.valueOf(1)),
                        new InfixExpression(InfixOp.Exp, new NumberExpression(BigDecimal.valueOf(2)), new NumberExpression(BigDecimal.valueOf(3)))),
                parser.parseExpressionWhole());
    }

    @Test
    public void parseExpression_mixedPrecedence() throws Exception {
        Parser parser = new Parser(new Token[]{
                new Token("1", Token.Type.Number),
                new Token("+", Token.Type.Operator),
                new Token("2", Token.Type.Number),
                new Token("*", Token.Type.Operator),
                new Token("3", Token.Type.Number),
                Token.EOF
        });
        assertEquals(
                new InfixExpression(InfixOp.Plus,
                        new NumberExpression(BigDecimal.valueOf(1)),
                        new InfixExpression(InfixOp.Times, new NumberExpression(BigDecimal.valueOf(2)), new NumberExpression(BigDecimal.valueOf(3)))),
                parser.parseExpressionWhole());
    }

    @Test
    public void parseExpression_prefixOperators() throws Exception {
        Parser parser = new Parser(new Token[]{
                new Token("+", Token.Type.Operator),
                new Token("-", Token.Type.Operator),
                new Token("~", Token.Type.Operator),
                new Token("!", Token.Type.Operator),
                new Token("foo", Token.Type.Identifier),
                Token.EOF
        });
        assertEquals(new PrefixExpression(PrefixOp.Positive, new PrefixExpression(PrefixOp.Negative, new PrefixExpression(PrefixOp.BitNegation, new PrefixExpression(PrefixOp.Not, new VarExpression("foo"))))),
                parser.parseExpressionWhole());
    }

    @Test
    public void parseExpression_negativeExpPrecedence() throws Exception {
        Parser parser = new Parser(new Token[]{
                new Token("-", Token.Type.Operator),
                new Token("foo", Token.Type.Identifier),
                new Token("**", Token.Type.Operator),
                new Token("2", Token.Type.Number),
                Token.EOF
        });
        assertEquals(new PrefixExpression(PrefixOp.Negative, new InfixExpression(InfixOp.Exp, new VarExpression("foo"), new NumberExpression(2))),
                parser.parseExpressionWhole());
    }

    @Test
    public void parseExpression_negativeTimesPrecedence() throws Exception {
        Parser parser = new Parser(new Token[]{
                new Token("-", Token.Type.Operator),
                new Token("foo", Token.Type.Identifier),
                new Token("*", Token.Type.Operator),
                new Token("2", Token.Type.Number),
                Token.EOF
        });
        assertEquals(new PrefixExpression(PrefixOp.Negative, new InfixExpression(InfixOp.Times, new VarExpression("foo"), new NumberExpression(2))),
                parser.parseExpressionWhole());
    }

    @Test
    public void parseExpression_negativePlusPrecedence() throws Exception {
        Parser parser = new Parser(new Token[]{
                new Token("-", Token.Type.Operator),
                new Token("foo", Token.Type.Identifier),
                new Token("+", Token.Type.Operator),
                new Token("2", Token.Type.Number),
                Token.EOF
        });
        assertEquals(new InfixExpression(InfixOp.Plus, new PrefixExpression(PrefixOp.Negative, new VarExpression("foo")), new NumberExpression(2)),
                parser.parseExpressionWhole());
    }

    @Test
    public void parseExpression_negationAndPrecedence() throws Exception {
        Parser parser = new Parser(new Token[]{
                new Token("!", Token.Type.Operator),
                new Token("a", Token.Type.Identifier),
                new Token("&&", Token.Type.Operator),
                new Token("a", Token.Type.Identifier),
                Token.EOF
        });
        assertEquals(new InfixExpression(InfixOp.And, new PrefixExpression(PrefixOp.Not, new VarExpression("a")), new VarExpression("a")),
                parser.parseExpressionWhole());
    }

    @Test
    public void parseExpression_parens() throws Exception {
        Parser parser = new Parser(new Token[]{
                new Token("(", Token.Type.LParen),
                new Token("foo", Token.Type.Identifier),
                new Token(")", Token.Type.RParen),
                Token.EOF
        });
        assertEquals(new VarExpression("foo"), parser.parseExpressionWhole());
    }
}
