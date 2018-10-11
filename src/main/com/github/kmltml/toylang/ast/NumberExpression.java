package com.github.kmltml.toylang.ast;

import com.github.kmltml.toylang.parsing.Token;
import com.github.kmltml.toylang.runtime.EvaluationException;
import com.github.kmltml.toylang.runtime.Scope;
import com.github.kmltml.toylang.runtime.Value;
import com.github.kmltml.toylang.runtime.value.NumberValue;

import java.math.BigDecimal;
import java.util.Objects;

public class NumberExpression extends Expression {

    private BigDecimal value;

    public NumberExpression(BigDecimal value) {
        this.value = value;
    }

    public NumberExpression(Token token) {
        if(!token.matches(Token.Type.Number))
            throw new IllegalArgumentException("A number expression can only be made from a number token");
        value = token.numberValue();
    }

    public NumberExpression(int i) {
        this(BigDecimal.valueOf(i));
    }

    @Override
    public Value<?, ?> evaluate(Scope scope) throws EvaluationException {
        return new NumberValue(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NumberExpression that = (NumberExpression) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return String.format("Number(%s)", value);
    }
}
