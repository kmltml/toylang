package com.github.kmltml.toylang.runtime.type;

import com.github.kmltml.toylang.ast.Expression;
import com.github.kmltml.toylang.parsing.InfixOp;
import com.github.kmltml.toylang.parsing.PrefixOp;
import com.github.kmltml.toylang.runtime.*;
import com.github.kmltml.toylang.runtime.value.InstanceValue;

import java.util.Objects;
import java.util.Optional;

public class ClassType extends Type<ClassType, InstanceValue> {

    private UserClass userClass;

    public ClassType(UserClass userClass) {
        this.userClass = userClass;
    }

    @Override
    public Value<?, ?> evalInfixOperator(InstanceValue self, InfixOp op, Expression right, Scope scope) throws EvaluationException {
        throw EvaluationException.unsupportedOperator(this, op);
    }

    @Override
    public Value<?, ?> evalPrefixOperator(InstanceValue self, PrefixOp op, Scope scope) throws EvaluationException {
        throw EvaluationException.unsupportedOperator(this, op);
    }

    @Override
    public Value<?, ?> getMethod(InstanceValue self, String name) throws EvaluationException {
        Optional<Value> meth = self.getMethods().getValue(name);
        if ("this".equals(name) || !meth.isPresent()) {
            throw EvaluationException.methodNotFound(name, this);
        }
        return meth.get();
    }

    public Object getName() {
        return userClass.getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassType classType = (ClassType) o;
        return Objects.equals(userClass, classType.userClass);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userClass);
    }

    @Override
    public String toString() {
        return String.format("Class(%s)", userClass.getName());
    }
}
