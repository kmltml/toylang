package com.github.kmltml.toylang.parsing;

import com.github.kmltml.toylang.parsing.Token.Type;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class TokenTest {

    @Test
    public void stringValue_simple() throws Exception {
        assertEquals("test", new Token("\"test\"", Type.String).stringValue());
    }

    @Test
    public void stringValue_quoteEscape() throws Exception {
        assertEquals("\"", new Token("\"\\\"\"", Type.String).stringValue());
    }

    @Test
    public void stringValue_newlineEscape() throws Exception {
        assertEquals("\n", new Token("\"\\n\"", Type.String).stringValue());
    }

    @Test
    public void stringValue_tabEscape() throws Exception {
        assertEquals("\t", new Token("\"\\t\"", Type.String).stringValue());
    }

    @Test
    public void stringValue_zeroEscape() throws Exception {
        assertEquals("\0", new Token("\"\\0\"", Type.String).stringValue());
    }


    @Test
    public void numberValue_integral() throws Exception {
        assertEquals(new BigDecimal(42), new Token("42", Type.Number).numberValue());
    }

    @Test
    public void numberValue_floating() throws Exception {
        assertEquals(new BigDecimal("0.42"), new Token("0.42", Type.Number).numberValue());
    }
}
