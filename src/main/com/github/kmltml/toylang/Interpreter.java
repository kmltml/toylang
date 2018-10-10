package com.github.kmltml.toylang;

import com.github.kmltml.toylang.ast.Expression;
import com.github.kmltml.toylang.parsing.*;
import com.github.kmltml.toylang.runtime.EvaluationException;
import com.github.kmltml.toylang.runtime.Scope;
import com.github.kmltml.toylang.runtime.Value;

public class Interpreter {

    private Scope scope = new Scope();

    public void addBinding(String name, Value value) {
        scope.putValue(name, value);
    }

    public Value interpretExpression(String source) throws LexingException, ParsingException, EvaluationException {
        Token[] tokens = new Tokenizer(source).tokenize();
        Expression expr = new Parser(tokens).parseExpressionWhole();
        return expr.evaluate(scope);
    }

}
