package com.github.kmltml.toylang.ast;

import com.github.kmltml.toylang.runtime.EvaluationException;
import com.github.kmltml.toylang.runtime.Scope;
import com.github.kmltml.toylang.runtime.Value;
import com.github.kmltml.toylang.runtime.value.UnitValue;

import java.util.Objects;

/**
 * A conditional expression, with an optional else branch.
 * When evaluated it first evaluates the condition expression, which must yield a value of type Bool.
 * If the condition was true, the true branch is evaluated, and its value yielded, otherwise the else
 * branch's value is yielded.
 * If there is no else branch, the unit will be yielded instead.
 */
public class IfExpression extends Expression {

    private final Expression condition;
    private final Expression ifTrue;
    private final Expression ifFalse;

    /**
     *
     * @param condition The boolean-valued expression to be checked.
     * @param ifTrue The true branch, evaluated when condition is true.
     * @param ifFalse The else branch, evaluated when condition is false. Can be null, if not present in the source.
     */
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
            return ifFalse != null ? ifFalse.evaluate(scope) : UnitValue.instance;
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
