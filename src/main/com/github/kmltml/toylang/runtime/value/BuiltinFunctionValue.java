package com.github.kmltml.toylang.runtime.value;

import com.github.kmltml.toylang.runtime.BuiltinFunction;
import com.github.kmltml.toylang.runtime.EvaluationException;
import com.github.kmltml.toylang.runtime.Value;

import java.util.List;
import java.util.OptionalInt;

public class BuiltinFunctionValue extends FunctionValue {

    private BuiltinFunction body;
    private OptionalInt expectedArgs;

    public BuiltinFunctionValue(BuiltinFunction body, OptionalInt expectedArgs) {
        this.body = body;
        this.expectedArgs = expectedArgs;
    }

    @Override
    public Value<?, ?> apply(List<Value<?, ?>> arguments) throws EvaluationException {
        if (expectedArgs.isPresent()) {
            if (arguments.size() != expectedArgs.getAsInt()) {
                throw EvaluationException.argumentCountMismatch(expectedArgs.getAsInt(), arguments.size());
            }
        }
        return body.apply(arguments);
    }

}
