package com.github.kmltml.toylang.ast;

import com.github.kmltml.toylang.runtime.EvaluationException;
import com.github.kmltml.toylang.runtime.Scope;
import com.github.kmltml.toylang.runtime.Value;

import java.util.Objects;

/**
 * An expression, that calls an object's method. It's represented in source by following an expression, that yields
 * an object, with a dot and an identifier naming the method. Available methods depend on the type of the object.
 */
public class MethodExpression extends Expression {

    private Expression left;
    private String name;

    /**
     *
     * @param left The expression yielding the object from which the method is to be selected.
     * @param name The name of the method.
     */
    public MethodExpression(Expression left, String name) {
        this.left = left;
        this.name = name;
    }

    @Override
    public Value<?, ?> evaluate(Scope scope) throws EvaluationException {
        Value<?, ?> leftVal = left.evaluate(scope);
        return leftVal.getMethod(name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MethodExpression that = (MethodExpression) o;
        return Objects.equals(left, that.left) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, name);
    }

    @Override
    public String toString() {
        return String.format("Method(%s, %s)", left, name);
    }
}
