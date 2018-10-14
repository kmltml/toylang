package com.github.kmltml.toylang.ast;

import com.github.kmltml.toylang.runtime.EvaluationException;
import com.github.kmltml.toylang.runtime.Scope;
import com.github.kmltml.toylang.runtime.UserClass;
import com.github.kmltml.toylang.runtime.Value;
import com.github.kmltml.toylang.runtime.value.UnitValue;

import java.util.List;
import java.util.Objects;

/**
 * Class definition statement. When evaluated it adds a new {@link UserClass}, to the scope, with given name and
 * body, and yields the unit value.
 */
public class ClassDefExpression extends Expression {

    private String name;
    private List<Expression> body;

    /**
     *
     * @param name The name of the newly defined UserClass
     * @param body The body of the class constructor, which is evaluated whenever
     *             an instance of this class is made. Any bindings introduced during
     *             that evaluation will be exposed as methods of the object.
     *             Inside the constructor <code>this</code> will be bound to the created object.
     */
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
