package com.github.kmltml.toylang.ast;

import com.github.kmltml.toylang.runtime.EvaluationException;
import com.github.kmltml.toylang.runtime.Scope;
import com.github.kmltml.toylang.runtime.Value;
import com.github.kmltml.toylang.runtime.value.LambdaValue;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class LambdaExpression extends Expression {

    private List<String> args;
    private Expression body;

    public LambdaExpression(List<String> args, Expression body) {
        this.args = args;
        this.body = body;
    }

    @Override
    public Value<?, ?> evaluate(Scope scope) throws EvaluationException {
        return new LambdaValue(scope, args, body);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LambdaExpression that = (LambdaExpression) o;
        return Objects.equals(args, that.args) &&
                Objects.equals(body, that.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(args, body);
    }

    @Override
    public String toString() {
        return String.format("Lambda((%s), %s)", args.stream().map(Object::toString).collect(Collectors.joining(", ")), body);
    }
}
