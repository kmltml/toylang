package com.github.kmltml.toylang.runtime.type;

import com.github.kmltml.toylang.ast.Expression;
import com.github.kmltml.toylang.parsing.InfixOp;
import com.github.kmltml.toylang.parsing.PrefixOp;
import com.github.kmltml.toylang.runtime.EvaluationException;
import com.github.kmltml.toylang.runtime.Scope;
import com.github.kmltml.toylang.runtime.Type;
import com.github.kmltml.toylang.runtime.Value;
import com.github.kmltml.toylang.runtime.value.FunctionValue;

public class FunctionType extends Type<FunctionType, FunctionValue> {

    public static final FunctionType instance = new FunctionType();

    private FunctionType() {

    }

    @Override
    public Value<?, ?> evalInfixOperator(FunctionValue self, InfixOp op, Expression right, Scope scope) throws EvaluationException {
        throw EvaluationException.unsupportedOperator(this, op);
    }

    @Override
    public Value<?, ?> evalPrefixOperator(FunctionValue self, PrefixOp op, Scope scope) throws EvaluationException {
        throw EvaluationException.unsupportedOperator(this, op);
    }

    @Override
    public Value<?, ?> getMethod(FunctionValue self, String name) throws EvaluationException {
        throw EvaluationException.methodNotFound(name, this);
    }
}
