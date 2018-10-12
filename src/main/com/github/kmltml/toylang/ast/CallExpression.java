package com.github.kmltml.toylang.ast;

import com.github.kmltml.toylang.runtime.EvaluationException;
import com.github.kmltml.toylang.runtime.Scope;
import com.github.kmltml.toylang.runtime.Value;
import com.github.kmltml.toylang.runtime.value.FunctionValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CallExpression extends Expression {

    private Expression function;
    private List<Expression> arguments;

    public CallExpression(Expression function, List<Expression> arguments) {
        this.function = function;
        this.arguments = arguments;
    }

    @Override
    public Value<?, ?> evaluate(Scope scope) throws EvaluationException {
        FunctionValue fval = function.evaluate(scope).requireFunction();
        List<Value<?, ?>> argVals = new ArrayList<>();
        for (Expression a : arguments) {
            argVals.add(a.evaluate(scope));
        }
        return fval.apply(argVals);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CallExpression that = (CallExpression) o;
        return Objects.equals(function, that.function) &&
                Objects.equals(arguments, that.arguments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(function, arguments);
    }

    @Override
    public String toString() {
        return String.format("Call(%s, (%s))", function, arguments.stream().map(Object::toString).collect(Collectors.joining(", ")));
    }
}
