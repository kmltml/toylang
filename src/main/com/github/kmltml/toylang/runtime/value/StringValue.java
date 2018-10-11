package com.github.kmltml.toylang.runtime.value;

import com.github.kmltml.toylang.runtime.Value;
import com.github.kmltml.toylang.runtime.type.StringType;

import java.util.Objects;

public class StringValue extends Value<StringValue, StringType> {

    private String value;

    public StringValue(String value) {
        this.value = value;
    }

    @Override
    public StringType getType() {
        return StringType.instance;
    }

    @Override
    public StringValue self() {
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StringValue that = (StringValue) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    public String getValue() {
        return value;
    }
}
