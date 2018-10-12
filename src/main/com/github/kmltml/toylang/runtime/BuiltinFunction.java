package com.github.kmltml.toylang.runtime;

import java.util.List;

public interface BuiltinFunction {

    Value<?, ?> apply(List<Value<?, ?>> args) throws EvaluationException;

}
