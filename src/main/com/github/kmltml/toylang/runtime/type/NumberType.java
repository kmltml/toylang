package com.github.kmltml.toylang.runtime.type;

import com.github.kmltml.toylang.ast.Expression;
import com.github.kmltml.toylang.parsing.InfixOp;
import com.github.kmltml.toylang.runtime.EvaluationException;
import com.github.kmltml.toylang.runtime.Scope;
import com.github.kmltml.toylang.runtime.Type;
import com.github.kmltml.toylang.runtime.Value;
import com.github.kmltml.toylang.runtime.value.BoolValue;
import com.github.kmltml.toylang.runtime.value.NumberValue;

import java.math.BigDecimal;

public class NumberType extends Type<NumberType, NumberValue> {

    public static final NumberType instance = new NumberType();

    private NumberType() {

    }

    @Override
    public Value<?, ?> evalInfixOperator(NumberValue self, InfixOp op, Expression right, Scope scope) throws EvaluationException {
        switch (op) {
            case Plus:
                return new NumberValue(self.getValue().add(right.evaluate(scope).requireNumber().getValue()));
            case Minus:
                return new NumberValue(self.getValue().subtract(right.evaluate(scope).requireNumber().getValue()));
            case Times:
                return new NumberValue(self.getValue().multiply(right.evaluate(scope).requireNumber().getValue()));
            case Div:
                return new NumberValue(self.getValue().divide(right.evaluate(scope).requireNumber().getValue()));
            case Mod:
                return new NumberValue(self.getValue().remainder(right.evaluate(scope).requireNumber().getValue()));
            case Or:
                return new NumberValue(new BigDecimal(self.getValue().toBigInteger().or(right.evaluate(scope).requireNumber().getValue().toBigInteger())));
            case And:
                return new NumberValue(new BigDecimal(self.getValue().toBigInteger().and(right.evaluate(scope).requireNumber().getValue().toBigInteger())));
            case Exp:
                BigDecimal rval = right.evaluate(scope).requireNumber().getValue();
                if (BigDecimal.valueOf(rval.intValue()).equals(rval)) {
                    return new NumberValue(self.getValue().pow(rval.intValue()));
                } else {
                    return new NumberValue(BigDecimal.valueOf(Math.pow(self.getValue().doubleValue(), rval.doubleValue())));
                }
            case Eq:
                return BoolValue.of(self.getValue().equals(right.evaluate(scope).requireNumber().getValue()));
            case Neq:
                return BoolValue.of(!self.getValue().equals(right.evaluate(scope).requireNumber().getValue()));
            case Less:
                return BoolValue.of(self.getValue().compareTo(right.evaluate(scope).requireNumber().getValue()) < 0);
            case Greater:
                return BoolValue.of(self.getValue().compareTo(right.evaluate(scope).requireNumber().getValue()) > 0);
            case LessEq:
                return BoolValue.of(self.getValue().compareTo(right.evaluate(scope).requireNumber().getValue()) <= 0);
            case GreaterEq:
                return BoolValue.of(self.getValue().compareTo(right.evaluate(scope).requireNumber().getValue()) >= 0);
            default:
                throw EvaluationException.unsupportedOperator(this, op);
        }
    }
}
