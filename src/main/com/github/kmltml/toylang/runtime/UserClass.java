package com.github.kmltml.toylang.runtime;

import com.github.kmltml.toylang.ast.Expression;

import java.util.List;
import java.util.Objects;

public class UserClass {

    private String name;
    private List<Expression> body;

    public UserClass(String name, List<Expression> body) {
        this.name = name;
        this.body = body;
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
