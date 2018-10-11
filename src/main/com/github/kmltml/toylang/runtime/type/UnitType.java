package com.github.kmltml.toylang.runtime.type;

import com.github.kmltml.toylang.ast.Expression;
import com.github.kmltml.toylang.parsing.InfixOp;
import com.github.kmltml.toylang.parsing.PrefixOp;
import com.github.kmltml.toylang.runtime.EvaluationException;
import com.github.kmltml.toylang.runtime.Scope;
import com.github.kmltml.toylang.runtime.Type;
import com.github.kmltml.toylang.runtime.Value;
import com.github.kmltml.toylang.runtime.value.UnitValue;

public class UnitType extends Type<UnitType, UnitValue> {

    public static final UnitType instance = new UnitType();

    private UnitType() {

    }

    @Override
    public Value<?, ?> evalInfixOperator(UnitValue self, InfixOp op, Expression right, Scope scope) throws EvaluationException {
        throw EvaluationException.unsupportedOperator(this, op);
    }

    @Override
    public Value<?, ?> evalPrefixOperator(UnitValue self, PrefixOp op, Scope scope) throws EvaluationException {
        throw EvaluationException.unsupportedOperator(this, op);
    }
}
