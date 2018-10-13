package com.github.kmltml.toylang.runtime.value;

import com.github.kmltml.toylang.ast.Expression;
import com.github.kmltml.toylang.runtime.EvaluationException;
import com.github.kmltml.toylang.runtime.Scope;
import com.github.kmltml.toylang.runtime.Value;

import java.util.List;
import java.util.Objects;

public class LambdaValue extends FunctionValue {

    private Scope scope;
    private List<String> argNames;
    private Expression body;

    public LambdaValue(Scope scope, List<String> argNames, Expression body) {
        this.scope = scope;
        this.argNames = argNames;
        this.body = body;
    }

    @Override
    public Value<?, ?> apply(List<Value<?, ?>> arguments) throws EvaluationException {
        if (arguments.size() != argNames.size()) {
            throw EvaluationException.argumentCountMismatch(argNames.size(), arguments.size());
        }
        Scope innerScope = new Scope(scope);
        for (int i = 0; i < argNames.size(); i++) {
            innerScope.putValue(argNames.get(i), arguments.get(i));
        }
        return body.evaluate(innerScope);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LambdaValue that = (LambdaValue) o;
        return Objects.equals(scope, that.scope) &&
                Objects.equals(argNames, that.argNames) &&
                Objects.equals(body, that.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(scope, argNames, body);
    }

    @Override
    public String toString() {
        return String.format("Lambda(%x, (%s), %s)", scope.hashCode(), argNames, body);
    }
}
