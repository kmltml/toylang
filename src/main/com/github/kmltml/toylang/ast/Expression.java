package com.github.kmltml.toylang.ast;

import com.github.kmltml.toylang.runtime.EvaluationException;
import com.github.kmltml.toylang.runtime.Scope;
import com.github.kmltml.toylang.runtime.Value;

public abstract class Expression {

    public abstract Value<?, ?> evaluate(Scope scope) throws EvaluationException;

    public void assign(Value<?, ?> v, Scope scope) throws EvaluationException {
        throw EvaluationException.cantAssign(this);
    }
}
