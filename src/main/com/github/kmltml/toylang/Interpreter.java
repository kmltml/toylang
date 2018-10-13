package com.github.kmltml.toylang;

import com.github.kmltml.toylang.ast.Expression;
import com.github.kmltml.toylang.parsing.*;
import com.github.kmltml.toylang.runtime.EvaluationException;
import com.github.kmltml.toylang.runtime.Scope;
import com.github.kmltml.toylang.runtime.Value;
import com.github.kmltml.toylang.runtime.value.BuiltinFunctionValue;
import com.github.kmltml.toylang.runtime.value.StringValue;
import com.github.kmltml.toylang.runtime.value.UnitValue;

import java.util.OptionalInt;
import java.util.Scanner;

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

    public Scope getScope() {
        return scope;
    }

    public static void main(String[] args) {
        Interpreter interpreter = new Interpreter();
        boolean[] running = new boolean[]{ true };
        interpreter.addBinding("exit", new BuiltinFunctionValue(a -> {
            running[0] = false;
            return UnitValue.instance;
        }, OptionalInt.of(0)));
        interpreter.addBinding("prompt", new StringValue("> "));
        Scanner scanner = new Scanner(System.in);
        while (running[0]) {
            Value prompt = interpreter.getScope().getValue("prompt").get();
            if (prompt instanceof StringValue) {
                System.out.print(((StringValue) prompt).getValue());
            } else {
                System.out.print("> ");
            }
            String line = scanner.nextLine();
            try {
                System.out.println(interpreter.interpretExpression(line));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
