package com.github.kmltml.toylang.runtime.type;

import com.github.kmltml.toylang.ast.Expression;
import com.github.kmltml.toylang.parsing.InfixOp;
import com.github.kmltml.toylang.runtime.EvaluationException;
import com.github.kmltml.toylang.runtime.Scope;
import com.github.kmltml.toylang.runtime.Type;
import com.github.kmltml.toylang.runtime.Value;
import com.github.kmltml.toylang.runtime.value.BoolValue;

public class BoolType extends Type<BoolType, BoolValue> {

    public static final BoolType instance = new BoolType();

    private BoolType() {

    }

    @Override
    public Value evalInfixOperator(BoolValue self, InfixOp op, Expression right, Scope scope) throws EvaluationException {
        switch (op) {
            case Or:
                return BoolValue.of(self.getValue() || right.evaluate(scope).requireBool().getValue());
            case And:
                return BoolValue.of(self.getValue() && right.evaluate(scope).requireBool().getValue());
            case Eq:
                return BoolValue.of(self.getValue() == right.evaluate(scope).requireBool().getValue());
            case Neq:
                return BoolValue.of(self.getValue() != right.evaluate(scope).requireBool().getValue());
            default:
                throw EvaluationException.unsupportedOperator(this, op);
        }
    }
}
