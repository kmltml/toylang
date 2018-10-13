package com.github.kmltml.toylang.runtime.value;

import com.github.kmltml.toylang.runtime.Scope;
import com.github.kmltml.toylang.runtime.Value;
import com.github.kmltml.toylang.runtime.type.ClassType;

import java.util.Objects;
import java.util.Set;

public class InstanceValue extends Value<InstanceValue, ClassType> {

    private ClassType type;
    private Scope methods;

    public InstanceValue(ClassType type, Scope methods) {
        this.type = type;
        this.methods = methods;
    }

    @Override
    public ClassType getType() {
        return type;
    }

    @Override
    public InstanceValue self() {
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InstanceValue that = (InstanceValue) o;
        return Objects.equals(type, that.type) &&
                Objects.equals(methods, that.methods);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, methods);
    }

    @Override
    public String toString() {
        Set<String> methods = this.methods.getBindings().keySet();
        methods.remove("this");
        return String.format("Instance(%s, %s)", type.getName(), methods);
    }

    public Scope getMethods() {
        return methods;
    }
}
