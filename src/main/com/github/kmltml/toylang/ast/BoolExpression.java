package com.github.kmltml.toylang.ast;

import com.github.kmltml.toylang.parsing.Token;
import com.github.kmltml.toylang.runtime.EvaluationException;
import com.github.kmltml.toylang.runtime.Scope;
import com.github.kmltml.toylang.runtime.Value;
import com.github.kmltml.toylang.runtime.value.BoolValue;

import java.util.Objects;

public class BoolExpression extends Expression {

    private boolean value;

    public BoolExpression(boolean value) {
        this.value = value;
    }

    public BoolExpression(Token token) {
        if (!token.matches(Token.Type.Keyword)) {
            throw new IllegalArgumentException("A boolean expression can only be made from a keyword token");
        }
        this.value = token.booleanValue();
    }

    @Override
    public Value<?, ?> evaluate(Scope scope) throws EvaluationException {
        return BoolValue.of(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoolExpression that = (BoolExpression) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return String.format("Boolean(%s)", value);
    }
}
