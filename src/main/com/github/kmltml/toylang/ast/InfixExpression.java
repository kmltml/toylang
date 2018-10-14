package com.github.kmltml.toylang.ast;

import com.github.kmltml.toylang.parsing.InfixOp;
import com.github.kmltml.toylang.runtime.EvaluationException;
import com.github.kmltml.toylang.runtime.Scope;
import com.github.kmltml.toylang.runtime.Value;
import com.github.kmltml.toylang.runtime.value.UnitValue;

import java.util.Objects;

/**
 * An infix operator application. The value of this expression depends on the type of left expression, which is always
 * evaluated. The right side is passed by-name (as an Expression instead of Value), to allow for
 * short-circuiting operator behavior.
 * The assignment operator is treated specially, as it always refers to the left-hand expression as an l-value, to assign
 * the value of right expression to it.
 */
public class InfixExpression extends Expression {

    private final InfixOp op;
    private final Expression left;
    private final Expression right;

    public InfixExpression(InfixOp op, Expression left, Expression right) {
        this.op = op;
        this.left = left;
        this.right = right;
    }

    @Override
    public Value<?, ?> evaluate(Scope scope) throws EvaluationException {
        if(op == InfixOp.Assign) {
            left.assign(right.evaluate(scope), scope);
            return UnitValue.instance;
        }
        Value lval = left.evaluate(scope);
        return lval.evalInfixOperator(op, right, scope);
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
