package com.github.kmltml.toylang.ast;

import com.github.kmltml.toylang.runtime.EvaluationException;
import com.github.kmltml.toylang.runtime.Scope;
import com.github.kmltml.toylang.runtime.Value;
import com.github.kmltml.toylang.runtime.value.UnitValue;

import java.util.Objects;

public class WhileExpression extends Expression {

    private Expression condition;
    private Expression body;

    public WhileExpression(Expression condition, Expression body) {
        this.condition = condition;
        this.body = body;
    }

    @Override
    public Value<?, ?> evaluate(Scope scope) throws EvaluationException {
        while (condition.evaluate(scope).requireBool().getValue()) {
            body.evaluate(scope);
        }
        return UnitValue.instance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WhileExpression that = (WhileExpression) o;
        return Objects.equals(condition, that.condition) &&
                Objects.equals(body, that.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(condition, body);
    }

    @Override
    public String toString() {
        return String.format("While(%s, %s)", condition, body);
    }
}
