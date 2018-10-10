package com.github.kmltml.toylang.parsing;

import com.github.kmltml.toylang.ast.NumberExpression;
import com.github.kmltml.toylang.ast.StringExpression;
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
}
