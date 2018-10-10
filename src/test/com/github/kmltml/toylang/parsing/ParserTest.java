package com.github.kmltml.toylang.parsing;

import com.github.kmltml.toylang.ast.BoolExpression;
import com.github.kmltml.toylang.ast.NumberExpression;
import com.github.kmltml.toylang.ast.StringExpression;
import com.github.kmltml.toylang.ast.VarExpression;
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
}
