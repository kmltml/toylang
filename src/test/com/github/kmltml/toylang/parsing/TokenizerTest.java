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


    @Test
    public void tokenize_integer() throws Exception {
        assertArrayEquals(new Token[]{
                new Token("42", Type.Number),
                Token.EOF
        }, new Tokenizer("42").tokenize());
    }

    @Test
    public void tokenize_floating() throws Exception {
        assertArrayEquals(new Token[]{
                new Token("0.42", Type.Number),
                Token.EOF
        }, new Tokenizer("0.42").tokenize());
    }

    @Test
    public void tokenize_numbersSeparated() throws Exception {
        assertArrayEquals(new Token[]{
                new Token("10", Type.Number),
                new Token("20", Type.Number),
                Token.EOF
        }, new Tokenizer("10 20").tokenize());
    }


    @Test
    public void tokenize_identifier() throws Exception {
        assertArrayEquals(new Token[] {
                new Token("test123$_", Type.Identifier),
                Token.EOF
        }, new Tokenizer(" test123$_  ").tokenize());
    }

    @Test
    public void tokenize_identifiersSeparated() throws Exception {
        assertArrayEquals(new Token[] {
                new Token("$test", Type.Identifier),
                new Token("_testToo", Type.Identifier),
                Token.EOF
        }, new Tokenizer("$test  _testToo").tokenize());
    }

    @Test
    public void tokenize_booleanKeywords() throws Exception {
        assertArrayEquals(new Token[]{
                new Token("true", Type.Keyword),
                new Token("false", Type.Keyword),
                Token.EOF
        }, new Tokenizer("true  false").tokenize());
    }

    @Test
    public void tokenize_miscKeywords() throws Exception {
        assertArrayEquals(new Token[] {
                new Token("var", Type.Keyword),
                Token.EOF
        }, new Tokenizer("var").tokenize());
    }

    @Test
    public void tokenize_controlFlowKeywords() throws Exception {
        assertArrayEquals(new Token[]{
                new Token("if", Type.Keyword),
                new Token("else", Type.Keyword),
                new Token("while", Type.Keyword),
                Token.EOF
        }, new Tokenizer("if else while").tokenize());
    }

    @Test
    public void tokenize_infixOperators() throws Exception {
        assertArrayEquals(new Token[]{
                new Token("+", Type.Operator),
                new Token("**", Type.Operator),
                new Token("=", Type.Operator),
                new Token("<=", Type.Operator),
                Token.EOF
        }, new Tokenizer("+**= <=").tokenize());
    }

    @Test
    public void tokenize_prefixOperators() throws Exception {
        assertArrayEquals(new Token[]{
                new Token("+", Type.Operator),
                new Token("-", Type.Operator),
                new Token("!", Type.Operator),
                new Token("~", Type.Operator),
                Token.EOF
        }, new Tokenizer("+ - ! ~").tokenize());
    }

    @Test
    public void tokenize_parens() throws Exception {
        assertArrayEquals(new Token[]{
                new Token("(", Type.LParen),
                new Token(")", Type.RParen),
                Token.EOF
        }, new Tokenizer("(  )").tokenize());
    }

    @Test
    public void tokenize_braces() throws Exception {
        assertArrayEquals(new Token[]{
                new Token("{", Type.LBrace),
                new Token("}", Type.RBrace),
                Token.EOF
        }, new Tokenizer("{  }").tokenize());
    }

    @Test
    public void tokenize_semicolon() throws Exception {
        assertArrayEquals(new Token[] {
                new Token(";", Type.Semicolon),
                Token.EOF
        }, new Tokenizer(";").tokenize());
    }
}
