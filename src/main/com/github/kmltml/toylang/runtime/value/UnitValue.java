package com.github.kmltml.toylang.runtime.value;

import com.github.kmltml.toylang.runtime.Value;
import com.github.kmltml.toylang.runtime.type.UnitType;

public class UnitValue extends Value<UnitValue, UnitType> {

    public static final UnitValue instance = new UnitValue();

    private UnitValue() {

    }

    @Override
    public UnitType getType() {
        return UnitType.instance;
    }

    @Override
    public UnitValue self() {
        return null;
    }
}
