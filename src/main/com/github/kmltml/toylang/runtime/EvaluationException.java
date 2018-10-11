package com.github.kmltml.toylang.runtime;

import com.github.kmltml.toylang.ast.Expression;
import com.github.kmltml.toylang.parsing.InfixOp;

public class EvaluationException extends Exception {

    public EvaluationException(String message) {
        super(message);
    }

    public static EvaluationException unknownVariable(String name) {
        return new EvaluationException(String.format("Variable %s is not defined in this context", name));
    }

    public static EvaluationException unsupportedOperator(Type<?, ?> type, InfixOp op) {
        return new EvaluationException(String.format("Value of type %s does not support the operator %s", type, op.getName()));
    }

    public static EvaluationException wrongType(Type<?, ?> required, Type<?, ?> actual) {
        return new EvaluationException(String.format("Operation requires value of type %s, but %s was supplied", required, actual));
    }

    public static EvaluationException cantAssign(Expression left) {
        return new EvaluationException(String.format("Can't assign to %s", left));
    }

}
