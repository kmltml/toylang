package com.github.kmltml.toylang.ast;

import com.github.kmltml.toylang.runtime.EvaluationException;
import com.github.kmltml.toylang.runtime.Scope;
import com.github.kmltml.toylang.runtime.Value;

/**
 * An executable fragment of code, that yields a value when evaluated.
 * Instances of this class are created by the parser.
 */
public abstract class Expression {

    /**
     * Run the expression represented by this object and return the resulting value.
     * @param scope The scope in which the expression should be evaluated.
     * @return The value yielded by the expression.
     * @throws EvaluationException Thrown on any runtime error, such as type mismatch or access to an undefined variable.
     */
    public abstract Value<?, ?> evaluate(Scope scope) throws EvaluationException;


    /**
     * Called, when this expression appears on the left side of an assignment operator.
     * By default it throws an exception, signalling, that this expression cannot be assigned to, since vast majority
     * of expressions are not valid in such position.
     * Thus, this method has to be overridden in subclasses representing valid l-values.
     * @param v The computed value of the right side of the assignment operator.
     * @param scope The scope in which the assignment should be done.
     * @throws EvaluationException Throw {@link EvaluationException#cantAssign(Expression)} if this is not a valid l-value.
     *         Regular evaluation exceptions may also be thrown normally.
     */
    public void assign(Value<?, ?> v, Scope scope) throws EvaluationException {
        throw EvaluationException.cantAssign(this);
    }
}
