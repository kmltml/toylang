package com.github.kmltml.toylang.ast;

import com.github.kmltml.toylang.runtime.EvaluationException;
import com.github.kmltml.toylang.runtime.Scope;
import com.github.kmltml.toylang.runtime.Value;

import java.util.Objects;

public class IfExpression extends Expression {

    private final Expression condition;
    private final Expression ifTrue;
    private final Expression ifFalse;

    public IfExpression(Expression condition, Expression ifTrue, Expression ifFalse) {
        this.condition = condition;
        this.ifTrue = ifTrue;
        this.ifFalse = ifFalse;
    }

    @Override
    public Value<?, ?> evaluate(Scope scope) throws EvaluationException {
        if (condition.evaluate(scope).requireBool().getValue()) {
            return ifTrue.evaluate(scope);
        } else {
            return ifFalse.evaluate(scope);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IfExpression that = (IfExpression) o;
        return Objects.equals(condition, that.condition) &&
                Objects.equals(ifTrue, that.ifTrue) &&
                Objects.equals(ifFalse, that.ifFalse);
    }

    @Override
    public int hashCode() {
        return Objects.hash(condition, ifTrue, ifFalse);
    }

    @Override
    public String toString() {
        return String.format("If(%s, %s, %s)", condition, ifTrue, ifFalse);
    }
}
