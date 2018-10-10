package com.github.kmltml.toylang;

import com.github.kmltml.toylang.ast.Expression;
import com.github.kmltml.toylang.parsing.*;
import com.github.kmltml.toylang.runtime.Value;

public class Interpreter {

    public Value interpretExpression(String source) throws LexingException, ParsingException {
        Token[] tokens = new Tokenizer(source).tokenize();
        Expression expr = new Parser(tokens).parseExpressionWhole();
        return expr.evaluate();
    }

}
