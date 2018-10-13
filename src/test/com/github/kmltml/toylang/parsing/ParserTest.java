package com.github.kmltml.toylang.parsing;

import com.github.kmltml.toylang.ast.*;
import com.github.kmltml.toylang.parsing.Token.Type;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class ParserTest {

    @Test
    public void parseExpression_stringLiteral() throws Exception {
        Parser parser = new Parser(new Token[]{
                new Token("\"Hello\"", Type.String),
                Token.EOF
        });
        assertEquals(new StringExpression("Hello"), parser.parseExpressionWhole());
    }

    @Test
    public void parseExpression_intLiteral() throws Exception {
        Parser parser = new Parser(new Token[]{
                new Token("42", Type.Number),
                Token.EOF
        });
        assertEquals(new NumberExpression(new BigDecimal(42)), parser.parseExpressionWhole());
    }

    @Test
    public void parseExpression_floatLiteral() throws Exception {
        Parser parser = new Parser(new Token[]{
                new Token("0.42", Type.Number),
                Token.EOF
        });
        assertEquals(new NumberExpression(new BigDecimal("0.42")), parser.parseExpressionWhole());
    }

    @Test
    public void parseExpression_variableAccess() throws Exception {
        Parser parser = new Parser(new Token[]{
                new Token("foo", Type.Identifier),
                Token.EOF
        });
        assertEquals(new VarExpression("foo"), parser.parseExpressionWhole());
    }

    @Test
    public void parseExpression_booleanLiteralTrue() throws Exception {
        Parser parser = new Parser(new Token[]{
                new Token("true", Type.Keyword),
                Token.EOF
        });
        assertEquals(new BoolExpression(true), parser.parseExpressionWhole());
    }

    @Test
    public void parseExpression_booleanLiteralFalse() throws Exception {
        Parser parser = new Parser(new Token[]{
                new Token("false", Type.Keyword),
                Token.EOF
        });
        assertEquals(new BoolExpression(false), parser.parseExpressionWhole());
    }

    @Test
    public void parseExpression_simpleInfix() throws Exception {
        Parser parser = new Parser(new Token[]{
                new Token("1", Type.Number),
                new Token("+", Type.Operator),
                new Token("2", Type.Number),
                Token.EOF
        });
        assertEquals(
                new InfixExpression(InfixOp.Plus, new NumberExpression(BigDecimal.valueOf(1)), new NumberExpression(BigDecimal.valueOf(2))),
                parser.parseExpressionWhole());
    }

    @Test
    public void parseExpression_infixl() throws Exception {
        Parser parser = new Parser(new Token[]{
                new Token("1", Type.Number),
                new Token("+", Type.Operator),
                new Token("2", Type.Number),
                new Token("+", Type.Operator),
                new Token("3", Type.Number),
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
                new Token("1", Type.Number),
                new Token("**", Type.Operator),
                new Token("2", Type.Number),
                new Token("**", Type.Operator),
                new Token("3", Type.Number),
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
                new Token("1", Type.Number),
                new Token("+", Type.Operator),
                new Token("2", Type.Number),
                new Token("*", Type.Operator),
                new Token("3", Type.Number),
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
                new Token("+", Type.Operator),
                new Token("-", Type.Operator),
                new Token("~", Type.Operator),
                new Token("!", Type.Operator),
                new Token("foo", Type.Identifier),
                Token.EOF
        });
        assertEquals(new PrefixExpression(PrefixOp.Positive, new PrefixExpression(PrefixOp.Negative, new PrefixExpression(PrefixOp.BitNegation, new PrefixExpression(PrefixOp.Not, new VarExpression("foo"))))),
                parser.parseExpressionWhole());
    }

    @Test
    public void parseExpression_negativeExpPrecedence() throws Exception {
        Parser parser = new Parser(new Token[]{
                new Token("-", Type.Operator),
                new Token("foo", Type.Identifier),
                new Token("**", Type.Operator),
                new Token("2", Type.Number),
                Token.EOF
        });
        assertEquals(new PrefixExpression(PrefixOp.Negative, new InfixExpression(InfixOp.Exp, new VarExpression("foo"), new NumberExpression(2))),
                parser.parseExpressionWhole());
    }

    @Test
    public void parseExpression_negativeTimesPrecedence() throws Exception {
        Parser parser = new Parser(new Token[]{
                new Token("-", Type.Operator),
                new Token("foo", Type.Identifier),
                new Token("*", Type.Operator),
                new Token("2", Type.Number),
                Token.EOF
        });
        assertEquals(new PrefixExpression(PrefixOp.Negative, new InfixExpression(InfixOp.Times, new VarExpression("foo"), new NumberExpression(2))),
                parser.parseExpressionWhole());
    }

    @Test
    public void parseExpression_negativePlusPrecedence() throws Exception {
        Parser parser = new Parser(new Token[]{
                new Token("-", Type.Operator),
                new Token("foo", Type.Identifier),
                new Token("+", Type.Operator),
                new Token("2", Type.Number),
                Token.EOF
        });
        assertEquals(new InfixExpression(InfixOp.Plus, new PrefixExpression(PrefixOp.Negative, new VarExpression("foo")), new NumberExpression(2)),
                parser.parseExpressionWhole());
    }

    @Test
    public void parseExpression_negationAndPrecedence() throws Exception {
        Parser parser = new Parser(new Token[]{
                new Token("!", Type.Operator),
                new Token("a", Type.Identifier),
                new Token("&&", Type.Operator),
                new Token("a", Type.Identifier),
                Token.EOF
        });
        assertEquals(new InfixExpression(InfixOp.And, new PrefixExpression(PrefixOp.Not, new VarExpression("a")), new VarExpression("a")),
                parser.parseExpressionWhole());
    }

    @Test
    public void parseExpression_parens() throws Exception {
        Parser parser = new Parser(new Token[]{
                new Token("(", Type.LParen),
                new Token("foo", Type.Identifier),
                new Token(")", Type.RParen),
                Token.EOF
        });
        assertEquals(new VarExpression("foo"), parser.parseExpressionWhole());
    }

    @Test
    public void parseExpression_ifThen() throws Exception {
        Parser parser = new Parser(new Token[]{
                new Token("if", Type.Keyword),
                new Token("(", Type.LParen),
                new Token("foo", Type.Identifier),
                new Token(")", Type.RParen),
                new Token("bar", Type.Identifier),
                Token.EOF
        });
        assertEquals(new IfExpression(new VarExpression("foo"), new VarExpression("bar"), null), parser.parseExpressionWhole());
    }

    @Test
    public void parseExpression_ifThenElse() throws Exception {
        Parser parser = new Parser(new Token[]{
                new Token("if", Type.Keyword),
                new Token("(", Type.LParen),
                new Token("foo", Type.Identifier),
                new Token(")", Type.RParen),
                new Token("bar", Type.Identifier),
                new Token("else", Type.Keyword),
                new Token("baz", Type.Identifier),
                Token.EOF
        });
        assertEquals(new IfExpression(new VarExpression("foo"), new VarExpression("bar"), new VarExpression("baz")), parser.parseExpressionWhole());
    }

    @Test
    public void parseExpression_block() throws Exception {
        Parser parser = new Parser(new Token[]{
                new Token("{", Type.LBrace),
                new Token("a", Type.Identifier),
                new Token(";", Type.Semicolon),
                new Token("b", Type.Identifier),
                new Token(";", Type.Semicolon),
                new Token("}", Type.RBrace),
                Token.EOF
        });
        assertEquals(new BlockExpression(Arrays.asList(
                new VarExpression("a"),
                new VarExpression("b")
        )), parser.parseExpressionWhole());
    }

    @Test
    public void parseExpression_varDef() throws Exception {
        Parser parser = new Parser(new Token[]{
                new Token("var", Type.Keyword),
                new Token("foo", Type.Identifier),
                new Token("=", Type.Operator),
                new Token("42", Type.Number),
                Token.EOF
        });
        assertEquals(new VarDefExpression("foo", new NumberExpression(42)), parser.parseExpressionWhole());
    }

    @Test
    public void parseExpression_while() throws Exception {
        Parser parser = new Parser(new Token[]{
                new Token("while", Type.Keyword),
                new Token("(", Type.LParen),
                new Token("cond", Type.Identifier),
                new Token(")", Type.RParen),
                new Token("body", Type.Identifier),
                Token.EOF
        });
        assertEquals(new WhileExpression(new VarExpression("cond"), new VarExpression("body")), parser.parseExpressionWhole());
    }

    @Test
    public void parseExpression_functionApplyEmpty() throws Exception {
        Parser parser = new Parser(new Token[]{
                new Token("foo", Type.Identifier),
                new Token("(", Type.LParen),
                new Token(")", Type.RParen),
                Token.EOF
        });
        assertEquals(new CallExpression(new VarExpression("foo"), Collections.emptyList()), parser.parseExpressionWhole());
    }

    @Test
    public void parseExpression_functionApplyOneArg() throws Exception {
        Parser parser = new Parser(new Token[]{
                new Token("foo", Type.Identifier),
                new Token("(", Type.LParen),
                new Token("bar", Type.Identifier),
                new Token(")", Type.RParen),
                Token.EOF
        });
        assertEquals(new CallExpression(new VarExpression("foo"), Collections.singletonList(new VarExpression("bar"))), parser.parseExpressionWhole());
    }

    @Test
    public void parseExpression_functionApplyTwoArgs() throws Exception {
        Parser parser = new Parser(new Token[]{
                new Token("foo", Type.Identifier),
                new Token("(", Type.LParen),
                new Token("bar", Type.Identifier),
                new Token(",", Type.Comma),
                new Token("baz", Type.Identifier),
                new Token(")", Type.RParen),
                Token.EOF
        });
        assertEquals(new CallExpression(new VarExpression("foo"), Arrays.asList(new VarExpression("bar"), new VarExpression("baz"))), parser.parseExpressionWhole());
    }

    @Test
    public void parseExpression_lambdaExpressionUnaryNoParen() throws Exception {
        Parser parser = new Parser(new Token[]{
                new Token("x", Type.Identifier),
                new Token("=>", Type.Arrow),
                new Token("x", Type.Identifier),
                Token.EOF

        });
        assertEquals(new LambdaExpression(Collections.singletonList("x"), new VarExpression("x")), parser.parseExpressionWhole());
    }


    @Test
    public void parseExpression_lambdaExpressionUnaryParen() throws Exception {
        Parser parser = new Parser(new Token[]{
                new Token("(", Type.LParen),
                new Token("x", Type.Identifier),
                new Token(")", Type.RParen),
                new Token("=>", Type.Arrow),
                new Token("x", Type.Identifier),
                Token.EOF

        });
        assertEquals(new LambdaExpression(Collections.singletonList("x"), new VarExpression("x")), parser.parseExpressionWhole());
    }

    @Test
    public void parseExpression_lambdaExpressionBinary() throws Exception {
        Parser parser = new Parser(new Token[]{
                new Token("(", Type.LParen),
                new Token("x", Type.Identifier),
                new Token(",", Type.Comma),
                new Token("y", Type.Identifier),
                new Token(")", Type.RParen),
                new Token("=>", Type.Arrow),
                new Token("y", Type.Identifier),
                Token.EOF

        });
        assertEquals(new LambdaExpression(Arrays.asList("x", "y"), new VarExpression("y")), parser.parseExpressionWhole());
    }

    @Test
    public void parseExpression_lambdaExpressionNullaryNoParen() throws Exception {
        Parser parser = new Parser(new Token[]{
                new Token("=>", Type.Arrow),
                new Token("x", Type.Identifier),
                Token.EOF

        });
        assertEquals(new LambdaExpression(Collections.emptyList(), new VarExpression("x")), parser.parseExpressionWhole());
    }


    @Test
    public void parseExpression_lambdaExpressionNullaryParen() throws Exception {
        Parser parser = new Parser(new Token[]{
                new Token("(", Type.LParen),
                new Token(")", Type.RParen),
                new Token("=>", Type.Arrow),
                new Token("x", Type.Identifier),
                Token.EOF

        });
        assertEquals(new LambdaExpression(Collections.emptyList(), new VarExpression("x")), parser.parseExpressionWhole());
    }

    @Test
    public void parseExpression_method() throws Exception {
        Parser parser = new Parser(new Token[]{
                new Token("foo", Type.Identifier),
                new Token(".", Type.Operator),
                new Token("bar", Type.Identifier),
                Token.EOF
        });
        assertEquals(new MethodExpression(new VarExpression("foo"), "bar"), parser.parseExpressionWhole());
    }

    @Test
    public void parseExpression_methodCall() throws Exception {
        Parser parser = new Parser(new Token[]{
                new Token("foo", Type.Identifier),
                new Token(".", Type.Operator),
                new Token("bar", Type.Identifier),
                new Token("(", Type.LParen),
                new Token("baz", Type.Identifier),
                new Token(")", Type.RParen),
                Token.EOF
        });
        assertEquals(new CallExpression(
                new MethodExpression(new VarExpression("foo"), "bar"),
                Collections.singletonList(new VarExpression("baz"))),
            parser.parseExpressionWhole());
    }

    @Test
    public void parseExpression_classDef() throws Exception {
        Parser parser = new Parser(new Token[]{
                new Token("class", Type.Keyword),
                new Token("Foo", Type.Identifier),
                new Token("{", Type.LBrace),
                new Token("var", Type.Keyword),
                new Token("bar", Type.Identifier),
                new Token("=", Type.Operator),
                new Token("2", Type.Number),
                new Token(";", Type.Semicolon),
                new Token("}", Type.RBrace),
                Token.EOF
        });
        assertEquals(new ClassDefExpression("Foo", Arrays.asList(
                new VarDefExpression("bar", new NumberExpression(2))
        )), parser.parseExpressionWhole());
    }
}
