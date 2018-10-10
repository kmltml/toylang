package com.github.kmltml.toylang.runtime;

public class EvaluationException extends Exception {

    public EvaluationException(String message) {
        super(message);
    }

    public static EvaluationException unknownVariable(String name) {
        return new EvaluationException(String.format("Variable %s is not defined in this context", name));
    }

}
