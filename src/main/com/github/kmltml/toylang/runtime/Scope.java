package com.github.kmltml.toylang.runtime;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class Scope {

    private Map<String, Value<?, ?>> bindings;
    private Map<String, UserClass> classes = new HashMap<>();
    private Optional<Scope> parent;

    public Scope(Scope parent) {
        this.parent = Optional.ofNullable(parent);
        bindings = new HashMap<>();
    }

    public Scope() {
        this(null);
    }

    public Optional<Value> getValue(String name) {
        if (bindings.containsKey(name)) {
            return Optional.of(bindings.get(name));
        } else {
            return parent.flatMap(p -> p.getValue(name));
        }
    }

    public void putValue(String name, Value<?, ?> value) {
        bindings.put(name, value);
    }

    public boolean isDefined(String name) {
        return bindings.containsKey(name) || parent.map(p -> p.isDefined(name)).orElse(false);
    }

    public void updateValue(String name, Value<?, ?> value) {
        if (bindings.containsKey(name)) {
            bindings.put(name, value);
        } else {
            parent.ifPresent(p -> p.updateValue(name, value));
        }
    }

    public UserClass getUserClass(String name) {
        UserClass ret = classes.get(name);
        if (ret == null && parent.isPresent()) {
            return parent.get().getUserClass(name);
        }
        return ret;
    }

    public void putUserClass(UserClass userClass) {
        classes.put(userClass.getName(), userClass);
    }

    public Map<String, Value<?,?>> getBindings() {
        return bindings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Scope scope = (Scope) o;
        return Objects.equals(bindings, scope.bindings) &&
                Objects.equals(classes, scope.classes) &&
                Objects.equals(parent, scope.parent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bindings, classes, parent);
    }
}
