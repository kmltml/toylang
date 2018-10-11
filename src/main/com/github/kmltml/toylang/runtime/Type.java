package com.github.kmltml.toylang.runtime;

import com.github.kmltml.toylang.ast.Expression;
import com.github.kmltml.toylang.parsing.InfixOp;
import com.github.kmltml.toylang.parsing.PrefixOp;

public abstract class Type<Self extends Type<Self, V>, V extends Value<V, Self>> {

    public abstract Value<?, ?> evalInfixOperator(V self, InfixOp op, Expression right, Scope scope) throws EvaluationException;

    public abstract Value<?, ?> evalPrefixOperator(V self, PrefixOp op, Scope scope) throws EvaluationException;

}
