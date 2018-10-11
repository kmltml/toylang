package com.github.kmltml.toylang.runtime;

import com.github.kmltml.toylang.ast.Expression;
import com.github.kmltml.toylang.parsing.InfixOp;
import com.github.kmltml.toylang.parsing.PrefixOp;
import com.github.kmltml.toylang.runtime.type.BoolType;
import com.github.kmltml.toylang.runtime.type.NumberType;
import com.github.kmltml.toylang.runtime.type.StringType;
import com.github.kmltml.toylang.runtime.value.BoolValue;
import com.github.kmltml.toylang.runtime.value.NumberValue;
import com.github.kmltml.toylang.runtime.value.StringValue;

public abstract class Value<Self extends Value<Self, T>, T extends Type<T, Self>> {

    public abstract T getType();

    public abstract Self self();

    public BoolValue requireBool() throws EvaluationException {
        if (getType() == BoolType.instance) {
            return (BoolValue) this;
        } else {
            throw EvaluationException.wrongType(BoolType.instance, getType());
        }
    }

    public NumberValue requireNumber() throws EvaluationException {
        if (getType() == NumberType.instance) {
            return (NumberValue) this;
        } else {
            throw EvaluationException.wrongType(NumberType.instance, getType());
        }
    }

    public StringValue requireString() throws EvaluationException {
        if (getType() == StringType.instance) {
            return (StringValue) this;
        } else {
            throw EvaluationException.wrongType(StringType.instance, getType());
        }
    }

    public Value<?, ?> evalInfixOperator(InfixOp op, Expression right, Scope scope) throws EvaluationException {
        return getType().evalInfixOperator(self(), op, right, scope);
    }

    public Value<?, ?> evalPrefixOperator(PrefixOp op, Scope scope) throws EvaluationException {
        return getType().evalPrefixOperator(self(), op, scope);
    }
}
