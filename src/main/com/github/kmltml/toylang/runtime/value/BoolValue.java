package com.github.kmltml.toylang.runtime.value;

import com.github.kmltml.toylang.runtime.Type;
import com.github.kmltml.toylang.runtime.Value;
import com.github.kmltml.toylang.runtime.type.BoolType;

import java.util.Objects;

public class BoolValue extends Value {

    private boolean value;

    public BoolValue(boolean value) {
        this.value = value;
    }

    @Override
    public Type getType() {
        return BoolType.instance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoolValue boolValue = (BoolValue) o;
        return value == boolValue.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

}
