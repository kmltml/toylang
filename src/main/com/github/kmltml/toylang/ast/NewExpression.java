package com.github.kmltml.toylang.ast;

import com.github.kmltml.toylang.runtime.EvaluationException;
import com.github.kmltml.toylang.runtime.Scope;
import com.github.kmltml.toylang.runtime.UserClass;
import com.github.kmltml.toylang.runtime.Value;

import java.util.Objects;

public class NewExpression extends Expression {

    private String name;

    public NewExpression(String name) {
        this.name = name;
    }

    @Override
    public Value<?, ?> evaluate(Scope scope) throws EvaluationException {
        UserClass userClass = scope.getUserClass(name);
        if (userClass == null) {
            throw EvaluationException.classNotFound(name);
        }
        return userClass.newInstance(scope);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewExpression that = (NewExpression) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return String.format("New(%s)", name);
    }
}
