package com.github.kmltml.toylang.ast;

import com.github.kmltml.toylang.parsing.InfixOp;
import com.github.kmltml.toylang.runtime.EvaluationException;
import com.github.kmltml.toylang.runtime.Scope;
import com.github.kmltml.toylang.runtime.Value;

import java.util.Objects;

public class InfixExpression implements Expression {

    private final InfixOp op;
    private final Expression left;
    private final Expression right;

    public InfixExpression(InfixOp op, Expression left, Expression right) {
        this.op = op;
        this.left = left;
        this.right = right;
    }

    @Override
    public Value evaluate(Scope scope) throws EvaluationException {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InfixExpression that = (InfixExpression) o;
        return op == that.op &&
                Objects.equals(left, that.left) &&
                Objects.equals(right, that.right);
    }

    @Override
    public int hashCode() {
        return Objects.hash(op, left, right);
    }

    @Override
    public String toString() {
        return String.format("Infix(%s, %s, %s)", op.getName(), left, right);
    }
}
