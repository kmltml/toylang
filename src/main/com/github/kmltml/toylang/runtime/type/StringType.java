package com.github.kmltml.toylang.runtime.type;

import com.github.kmltml.toylang.ast.Expression;
import com.github.kmltml.toylang.parsing.InfixOp;
import com.github.kmltml.toylang.parsing.PrefixOp;
import com.github.kmltml.toylang.runtime.EvaluationException;
import com.github.kmltml.toylang.runtime.Scope;
import com.github.kmltml.toylang.runtime.Type;
import com.github.kmltml.toylang.runtime.Value;
import com.github.kmltml.toylang.runtime.value.BoolValue;
import com.github.kmltml.toylang.runtime.value.NumberValue;
import com.github.kmltml.toylang.runtime.value.StringValue;

public class StringType extends Type<StringType, StringValue> {

    public static final StringType instance = new StringType();

    private StringType() {}

    @Override
    public Value evalInfixOperator(StringValue self, InfixOp op, Expression right, Scope scope) throws EvaluationException {
        switch (op) {
            case Plus:
                return new StringValue(self.getValue() + right.evaluate(scope).requireString().getValue());
            case Times:
                StringBuilder s = new StringBuilder();
                int r = right.evaluate(scope).requireNumber().getValue().intValue();
                for (int i = 0; i < r; i++) {
                    s.append(self.getValue());
                }
                return new StringValue(s.toString());
            case Eq:
                return BoolValue.of(self.getValue().equals(right.evaluate(scope).requireString().getValue()));
            case Neq:
                return BoolValue.of(!self.getValue().equals(right.evaluate(scope).requireString().getValue()));
            default:
                throw EvaluationException.unsupportedOperator(this, op);
        }
    }

    @Override
    public Value<?, ?> evalPrefixOperator(StringValue self, PrefixOp op, Scope scope) throws EvaluationException {
        throw EvaluationException.unsupportedOperator(this, op);
    }

    @Override
    public Value<?, ?> getMethod(StringValue self, String name) throws EvaluationException {
        switch (name) {
            case "length":
                return new NumberValue(self.getValue().length());
            default:
                throw EvaluationException.methodNotFound(name, this);
        }
    }
}
