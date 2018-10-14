package com.github.kmltml.toylang.ast;

import com.github.kmltml.toylang.runtime.EvaluationException;
import com.github.kmltml.toylang.runtime.Scope;
import com.github.kmltml.toylang.runtime.Value;
import com.github.kmltml.toylang.runtime.value.UnitValue;

import java.util.Objects;

/**
 * A variable definition expression. It creates a new variable binding with given name
 * (shadowing the previous one, if it already existed), and assigns to it the value of
 * the given initializer expression.
 */
public class VarDefExpression extends Expression {

    private String name;
    private Expression right;

    public VarDefExpression(String name, Expression right) {
        this.name = name;
        this.right = right;
    }

    @Override
    public Value<?, ?> evaluate(Scope scope) throws EvaluationException {
        scope.putValue(name, right.evaluate(scope));
        return UnitValue.instance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VarDefExpression that = (VarDefExpression) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(right, that.right);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, right);
    }

    @Override
    public String toString() {
        return String.format("VarDef(%s, %s)", name, right);
    }
}
