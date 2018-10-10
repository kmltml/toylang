package com.github.kmltml.toylang.runtime.value;

import com.github.kmltml.toylang.runtime.Type;
import com.github.kmltml.toylang.runtime.Value;
import com.github.kmltml.toylang.runtime.type.NumberType;

import java.math.BigDecimal;
import java.util.Objects;

public class NumberValue extends Value {

    private BigDecimal value;

    public NumberValue(BigDecimal value) {
        this.value = value;
    }

    public NumberValue(int i) {
        this(new BigDecimal(i));
    }

    public NumberValue(String v) {
        this(new BigDecimal(v));
    }

    @Override
    public Type getType() {
        return NumberType.instance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NumberValue that = (NumberValue) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return String.format("Number(%s)", value);
    }
}
