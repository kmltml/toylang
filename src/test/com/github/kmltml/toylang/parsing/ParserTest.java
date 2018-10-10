package com.github.kmltml.toylang.parsing;

import com.github.kmltml.toylang.ast.StringExpression;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ParserTest {

    @Test
    public void parseExpression_stringLiteral() throws Exception {
        Parser parser = new Parser(new Token[]{
                new Token("\"Hello\"", Token.Type.String)
        });
        assertEquals(new StringExpression("Hello"), parser.parseExpression());
        assertTrue(parser.isAtEnd());
    }


}
