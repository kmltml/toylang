package com.github.kmltml.toylang.ast;

import com.github.kmltml.toylang.parsing.PrefixOp;
import com.github.kmltml.toylang.runtime.EvaluationException;
import com.github.kmltml.toylang.runtime.Scope;
import com.github.kmltml.toylang.runtime.Value;

import java.util.Objects;

/**
 * A prefix operator application.
 * The behavior of the operator depends on the type of the right-hand expression.
 */
public class PrefixExpression extends Expression {

    private PrefixOp op;
    private Expression right;

    public PrefixExpression(PrefixOp op, Expression right) {
        this.op = op;
        this.right = right;
    }

    @Override
    public Value<?, ?> evaluate(Scope scope) throws EvaluationException {
        return right.evaluate(scope).evalPrefixOperator(op, scope);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrefixExpression that = (PrefixExpression) o;
        return op == that.op &&
                Objects.equals(right, that.right);
    }

    @Override
    public int hashCode() {
        return Objects.hash(op, right);
    }

    @Override
    public String toString() {
        return String.format("Prefix(%s, %s)", op.getName(), right);
    }
}
