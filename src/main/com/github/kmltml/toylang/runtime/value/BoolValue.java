package com.github.kmltml.toylang.runtime.value;

import com.github.kmltml.toylang.runtime.Value;
import com.github.kmltml.toylang.runtime.type.BoolType;

import java.util.Objects;

public class BoolValue extends Value<BoolValue, BoolType> {

    private boolean value;

    private BoolValue(boolean value) {
        this.value = value;
    }

    public static final BoolValue True = new BoolValue(true);
    public static final BoolValue False = new BoolValue(false);
    public static BoolValue of(boolean value) {
        return value ? True : False;
    }

    @Override
    public BoolType getType() {
        return BoolType.instance;
    }

    @Override
    public BoolValue self() {
        return this;
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

    public boolean getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.format("Bool(%s)", value);
    }

}
