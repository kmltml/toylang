package com.github.kmltml.toylang.parsing;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * An atomic part of the source code, consisting of the source text and a token type.
 */
public class Token {

    public static final Token EOF = new Token("", Type.Eof);

    private String source;
    private Type type;

    public Token(String source, Type type) {
        this.source = source;
        this.type = type;
    }

    /**
     * The text from source code that this token represents.
     */
    public String getSource() {
        return source;
    }

    /**
     * The type of a token
     */
    public enum Type {
        /**
         * End of file. A special token emitted once at the end of token stream
         */
        Eof,
        /**
         * An identifier, such as a variable name or a method name
         */
        Identifier,
        /**
         * A number, integral or with a decimal point
         */
        Number,
        /**
         * A keyword, as defined in the {@link Keyword} enum
         */
        Keyword,
        /**
         * A string literal, surrounded by double quotes ("")
         */
        String,
        /**
         * An operator, infix or prefix
         */
        Operator,
        /**
         * The left round paren '('
         */
        LParen,
        /**
         * The right round paren ')'
         */
        RParen,
        /**
         * The left curly brace '{'
         */
        LBrace,
        /**
         * The right curly brace '}'
         */
        RBrace,
        /**
         * The semicolon ';'
         */
        Semicolon,
        /**
         * The comma ','
         */
        Comma,
        /**
         * The lambda fat arrow '=>'
         */
        Arrow
    }

    @Override
    public String toString() {
        return "'" + source + "'(" + type + ")";
    }

    public Type getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Token token = (Token) o;
        return Objects.equals(source, token.source) &&
                type == token.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(source, type);
    }

    /**
     * Checks, if the token matches the given type.
     * @param t Expected token type.
     * @return true, if this token's type is the same as the given one.
     */
    public boolean matches(Type t) {
        return t == type;
    }

    /**
     * Checks, if the token is the given keyword.
     * @param k The expected keyword.
     * @return true, if this token is of type {@link Type#Keyword} and its keyword value matches the given one.
     */
    public boolean matchesKeyword(Keyword k) {
        return type == Type.Keyword && keywordValue() == k;
    }

    /**
     * Returns the value of the string literal.
     * Should only be called on tokens of type {@link Type#String}
     */
    public String stringValue() {
        StringBuilder ret = new StringBuilder();
        int i = 0;
        while(source.charAt(i) != '"') i++;
        i++;
        while(source.charAt(i) != '"') {
            if(source.charAt(i) == '\\') {
                i++;
                char c = source.charAt(i);
                switch (c) {
                    case 'n': ret.append('\n'); break;
                    case 't': ret.append('\t'); break;
                    case '0': ret.append('\0'); break;
                    default: ret.append(c); break;
                }
            } else {
                ret.append(source.charAt(i));
            }
            i++;
        }
        return ret.toString();
    }

    /**
     * Returns the numeric value of this token.
     * Should only be called on tokens of type {@link Type#Number}
     */
    public BigDecimal numberValue() {
        return new BigDecimal(source);
    }

    /**
     * Returns the name of the identifier represented by this token.
     * Should only be called on tokens of type {@link Type#Identifier}
     */
    public String identifierName() {
        return source.trim();
    }

    /**
     * Returns the keyword represented by this token
     * Should only be called on tokens of type {@link Type#Keyword}
     */
    public Keyword keywordValue() {
        return Keyword.forSource(source);
    }

    /**
     * Returns the boolean value represented by this token.
     * Should only be called on keywords 'true' and 'false'
     */
    public boolean booleanValue() {
        Keyword key = keywordValue();
        switch (key) {
            case True:
                return true;
            case False:
                return false;
            default:
                throw new IllegalStateException("booleanValue is only defined for keywords true and false");
        }
    }

    /**
     * Returns the infix operator represented by this token.
     * Should only be called on tokens of type {@link Type#Operator}
     */
    public InfixOp infixOpValue() {
        return InfixOp.fromSource(source);
    }

    /**
     * Returns the prefix operator represented by this token.
     * Should only be called on tokens of type {@link Type#Operator}
     */
    public PrefixOp prefixOpValue() {
        return PrefixOp.fromSource(source);
    }

}
