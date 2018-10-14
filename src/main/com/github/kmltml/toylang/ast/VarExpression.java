package com.github.kmltml.toylang.ast;

import com.github.kmltml.toylang.parsing.Token;
import com.github.kmltml.toylang.runtime.EvaluationException;
import com.github.kmltml.toylang.runtime.Scope;
import com.github.kmltml.toylang.runtime.Value;

import java.util.Objects;

/**
 * An expression getting the value of a variable binding from the scope.
 * When assigned to, it attempts to change the value of an existing binding in the current scope, or
 * any of its parents, never creating new bindings.
 */
public class VarExpression extends Expression {

    private String name;

    public VarExpression(String name) {
        this.name = name;
    }

    public VarExpression(Token token) {
        if (!token.matches(Token.Type.Identifier)) {
            throw new IllegalArgumentException("Var expression can only be made from a identifier token");
        }
        this.name = token.identifierName();
    }

    @Override
    public Value<?, ?> evaluate(Scope scope) throws EvaluationException {
        return scope.getValue(name).orElseThrow(() -> EvaluationException.unknownVariable(name));
    }

    @Override
    public void assign(Value<?, ?> v, Scope scope) throws EvaluationException {
        if (scope.isDefined(name)) {
            scope.updateValue(name, v);
        } else {
            throw EvaluationException.unknownVariable(name);
        }
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VarExpression that = (VarExpression) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return String.format("Var(%s)", name);
    }

}
