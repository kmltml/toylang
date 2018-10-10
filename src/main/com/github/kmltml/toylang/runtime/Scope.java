package com.github.kmltml.toylang.runtime;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Scope {

    private Map<String, Value> bindings;
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

    public void putValue(String name, Value value) {
        bindings.put(name, value);
    }
}
