package com.github.kmltml.toylang.runtime;

import com.github.kmltml.toylang.ast.Expression;
import com.github.kmltml.toylang.parsing.InfixOp;

public abstract class Type<Self extends Type<Self, V>, V extends Value<V, Self>> {

    public abstract Value<?, ?> evalInfixOperator(V self, InfixOp op, Expression right, Scope scope) throws EvaluationException;

}
