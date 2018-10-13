package com.github.kmltml.toylang.runtime;

import com.github.kmltml.toylang.ast.Expression;
import com.github.kmltml.toylang.runtime.type.ClassType;
import com.github.kmltml.toylang.runtime.value.InstanceValue;

import java.util.List;
import java.util.Objects;

public class UserClass {

    private String name;
    private List<Expression> body;

    public UserClass(String name, List<Expression> body) {
        this.name = name;
        this.body = body;
    }

    public InstanceValue newInstance(Scope scope) throws EvaluationException {
        Scope bodyScope = new Scope(scope);
        InstanceValue ret = new InstanceValue(new ClassType(this), bodyScope);
        bodyScope.putValue("this", ret);
        for (Expression expression : body) {
            expression.evaluate(bodyScope);
        }
        return ret;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserClass userClass = (UserClass) o;
        return Objects.equals(name, userClass.name) &&
                Objects.equals(body, userClass.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, body);
    }

    @Override
    public String toString() {
        return String.format("Class(%s, %s)", name, body);
    }

    public String getName() {
        return name;
    }
}
