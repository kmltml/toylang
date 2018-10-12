package com.github.kmltml.toylang.runtime.value;

import com.github.kmltml.toylang.runtime.EvaluationException;
import com.github.kmltml.toylang.runtime.Value;
import com.github.kmltml.toylang.runtime.type.FunctionType;

import java.util.List;

public abstract class FunctionValue extends Value<FunctionValue, FunctionType> {

    public abstract Value<?, ?> apply(List<Value<?, ?>> arguments) throws EvaluationException;

    @Override
    public FunctionType getType() {
        return FunctionType.instance;
    }

    @Override
    public FunctionValue self() {
        return this;
    }
}
