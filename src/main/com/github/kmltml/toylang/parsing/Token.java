package com.github.kmltml.toylang.parsing;

import java.math.BigDecimal;
import java.util.Objects;

public class Token {

    public static final Token EOF = new Token("", Type.Eof);

    private String source;
    private Type type;

    public Token(String source, Type type) {
        this.source = source;
        this.type = type;
    }

    public enum Type {
        Eof, Identifier, Number, Keyword, String, Operator
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

    public boolean matches(Type t) {
        return t == type;
    }

    public boolean matchesKeyword(Keyword k) {
        return type == Type.Keyword && keywordValue() == k;
    }

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

    public BigDecimal numberValue() {
        return new BigDecimal(source);
    }

    public String identifierName() {
        return source.trim();
    }

    public Keyword keywordValue() {
        return Keyword.forSource(source);
    }

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

    public InfixOp infixOpValue() {
        return InfixOp.fromSource(source);
    }

}
