package com.github.kmltml.toylang.ast;

import com.github.kmltml.toylang.runtime.EvaluationException;
import com.github.kmltml.toylang.runtime.Scope;
import com.github.kmltml.toylang.runtime.UserClass;
import com.github.kmltml.toylang.runtime.Value;
import com.github.kmltml.toylang.runtime.value.UnitValue;

import java.util.List;
import java.util.Objects;

public class ClassDefExpression extends Expression {

    private String name;
    private List<Expression> body;

    public ClassDefExpression(String name, List<Expression> body) {
        this.name = name;
        this.body = body;
    }

    @Override
    public Value<?, ?> evaluate(Scope scope) throws EvaluationException {
        scope.putUserClass(new UserClass(name, body));
        return UnitValue.instance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassDefExpression that = (ClassDefExpression) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(body, that.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, body);
    }

    @Override
    public String toString() {
        return String.format("Class(%s, %s)", name, body);
    }
}
