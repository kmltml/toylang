package com.github.kmltml.toylang.runtime;

import com.github.kmltml.toylang.ast.Expression;
import com.github.kmltml.toylang.parsing.InfixOp;
import com.github.kmltml.toylang.parsing.PrefixOp;

public class EvaluationException extends Exception {

    public EvaluationException(String message) {
        super(message);
    }

    public static EvaluationException unknownVariable(String name) {
        return new EvaluationException(String.format("Variable %s is not defined in this context", name));
    }

    public static EvaluationException unsupportedOperator(Type<?, ?> type, InfixOp op) {
        return new EvaluationException(String.format("Value of type %s does not support the infix operator %s", type, op.getName()));
    }

    public static EvaluationException unsupportedOperator(Type<?, ?> type, PrefixOp op) {
        return new EvaluationException(String.format("Value of type %s does not support the prefix operator %s", type, op.getName()));
    }

    public static EvaluationException wrongType(Type<?, ?> required, Type<?, ?> actual) {
        return new EvaluationException(String.format("Operation requires value of type %s, but %s was supplied", required, actual));
    }

    public static EvaluationException cantAssign(Expression left) {
        return new EvaluationException(String.format("Can't assign to %s", left));
    }

    public static EvaluationException argumentCountMismatch(int expected, int actual) {
        return new EvaluationException(String.format("Wrong number of arguments given, %d expected, but %d where given", expected, actual));
    }

    public static EvaluationException methodNotFound(String method, Type<?, ?> type) {
        return new EvaluationException(String.format("No method '%s' defined on type %s", method, type));
    }

    public static EvaluationException classNotFound(String name) {
        return new EvaluationException(String.format("No class '%s' defined", name));
    }
}
