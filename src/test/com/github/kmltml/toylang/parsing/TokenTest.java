package com.github.kmltml.toylang.parsing;

import com.github.kmltml.toylang.parsing.Token.Type;
import org.junit.Test;

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
}
