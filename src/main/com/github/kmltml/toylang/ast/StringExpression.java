package com.github.kmltml.toylang.ast;

import com.github.kmltml.toylang.parsing.Token;
import com.github.kmltml.toylang.runtime.EvaluationException;
import com.github.kmltml.toylang.runtime.Scope;
import com.github.kmltml.toylang.runtime.Value;
import com.github.kmltml.toylang.runtime.value.StringValue;

import java.util.Objects;

public class StringExpression implements Expression {

    private String value;

    public StringExpression(String value) {
        this.value = value;
    }

    public StringExpression(Token token) {
        if(!token.matches(Token.Type.String))
            throw new IllegalArgumentException("A string expression can only be made from a string token");
        value = token.stringValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StringExpression that = (StringExpression) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return String.format("String(%s)", value);
    }

    @Override
    public Value evaluate(Scope scope) throws EvaluationException {
        return new StringValue(value);
    }
}
