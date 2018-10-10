package com.github.kmltml.toylang.parsing;

import com.github.kmltml.toylang.parsing.Token.Type;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class TokenizerTest {

    @Test
    public void tokenize_singleString() throws Exception {
        assertArrayEquals(new Token[]{
                new Token("\"Hello\"", Type.String),
                Token.EOF
        }, new Tokenizer("\"Hello\"").tokenize());
    }

    @Test
    public void tokenize_twoStrings() throws Exception {
        assertArrayEquals(new Token[]{
                new Token("\"a\"", Type.String),
                new Token("\"b\"", Type.String),
                Token.EOF
        }, new Tokenizer("\"a\"\"b\"").tokenize());
    }

    @Test
    public void tokenize_twoStringsWhitespace() throws Exception {
        assertArrayEquals(new Token[]{
                new Token("\"a\"", Type.String),
                new Token("\"b\"", Type.String),
                Token.EOF
        }, new Tokenizer("\"a\" \t\n    \"b\"").tokenize());
    }

    @Test
    public void tokenize_stringEscape() throws Exception {
        assertArrayEquals(new Token[]{
                new Token("\"x\\n\"", Type.String),
                Token.EOF
        }, new Tokenizer("\"x\\n\"").tokenize());
    }

    @Test
    public void tokenize_stringQuoteEscape() throws Exception {
        assertArrayEquals(new Token[]{
                new Token("\"x\\\"\"", Type.String),
                Token.EOF
        }, new Tokenizer("\"x\\\"\"").tokenize());
    }

}