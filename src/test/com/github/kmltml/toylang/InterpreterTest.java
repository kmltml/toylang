package com.github.kmltml.toylang;

import com.github.kmltml.toylang.runtime.value.*;
import org.junit.Test;

import java.util.OptionalInt;

import static org.junit.Assert.assertEquals;

public class InterpreterTest {

    @Test
    public void interpretExpression_stringLiteral() throws Exception {
        assertEquals(new StringValue("Hello"), new Interpreter().interpretExpression("\"Hello\""));
    }

    @Test
    public void interpretExpression_intLiteral() throws Exception {
        assertEquals(new NumberValue(42), new Interpreter().interpretExpression("\t42 "));
    }

    @Test
    public void interpretExpression_floatLiteral() throws Exception {
        assertEquals(new NumberValue("0.42"), new Interpreter().interpretExpression("0.42"));
    }

    @Test
    public void interpretExpression_varAccess() throws Exception {
        Interpreter interpreter = new Interpreter();
        interpreter.addBinding("foo", new NumberValue(42));
        assertEquals(new NumberValue(42), interpreter.interpretExpression("foo"));
    }

    @Test
    public void interpretExpression_boolLiterals() throws Exception {
        Interpreter interpreter = new Interpreter();
        assertEquals(BoolValue.True, interpreter.interpretExpression("true"));
        assertEquals(BoolValue.False, interpreter.interpretExpression("false"));
    }

    @Test
    public void interpretExpression_numberInfix() throws Exception {
        assertEquals(new NumberValue(73), new Interpreter().interpretExpression("2 + 3 * 5 ** 2 - 8 / 2"));
    }

    @Test
    public void interpretExpression_numberComparison() throws Exception {
        assertEquals(BoolValue.True, new Interpreter().interpretExpression("2 + 2 < 20 / 4"));
        assertEquals(BoolValue.True, new Interpreter().interpretExpression("2 + 2 == 20 / 5"));
    }

    @Test
    public void interpretExpression_stringInfix() throws Exception {
        assertEquals(new StringValue("nananananananananananananana batman"),
                new Interpreter().interpretExpression("\"na\" * 14 + \" batman\""));
    }

    @Test
    public void interpretExpression_assign() throws Exception {
        Interpreter interpreter = new Interpreter();
        interpreter.addBinding("foo", new NumberValue(10));
        assertEquals(UnitValue.instance, interpreter.interpretExpression("foo = foo + 10"));
        assertEquals(new NumberValue(20), interpreter.interpretExpression("foo"));
    }

    @Test
    public void interpretExpression_prefixNot() throws Exception {
        assertEquals(BoolValue.True, new Interpreter().interpretExpression("!false"));
    }

    @Test
    public void interpretExpression_prefixNegative() throws Exception {
        assertEquals(new NumberValue(-50), new Interpreter().interpretExpression("-20 + -30"));
    }

    @Test
    public void interpretExpression_prefixNotParen() throws Exception {
        assertEquals(BoolValue.True, new Interpreter().interpretExpression("!(20 > 30)"));
    }

    @Test
    public void interpretExpression_prefixNegativeParen() throws Exception {
        assertEquals(new NumberValue(50), new Interpreter().interpretExpression("-(-20 + -30)"));
    }

    @Test
    public void interpretExpression_ifThenElse() throws Exception {
        Interpreter interpreter = new Interpreter();
        interpreter.addBinding("x", new NumberValue(-10));
        assertEquals(new NumberValue(10), interpreter.interpretExpression("if(x < 0) -x else x"));
        interpreter.addBinding("x", new NumberValue(10));
        assertEquals(new NumberValue(10), interpreter.interpretExpression("if(x < 0) -x else x"));
    }

    @Test
    public void interpretExpression_block() throws Exception {
        Interpreter interpreter = new Interpreter();
        interpreter.addBinding("foo", new NumberValue(10));
        assertEquals(new NumberValue(40), interpreter.interpretExpression("{ foo = foo + 10; foo * 2; }"));
        assertEquals(new NumberValue(20), interpreter.interpretExpression("foo"));
    }

    @Test
    public void interpretExpression_blockWithVars() throws Exception {
        Interpreter interpreter = new Interpreter();
        assertEquals(new NumberValue(60), interpreter.interpretExpression("{ var x = 10; x = x + 20; var y = x * 2; var x = true; if(x) y else y * 2; }"));
    }

    @Test
    public void interpretExpression_while() throws Exception {
        Interpreter interpreter = new Interpreter();
        assertEquals(new NumberValue(5050), interpreter.interpretExpression(
                "{\n" +
                "  var i = 1;\n" +
                "  var sum = 0;\n" +
                "  while(i <= 100) {\n" +
                "    sum = sum + i;\n" +
                "    i = i + 1;\n" +
                "  };\n" +
                "  sum;\n" +
                "}"));
    }

    @Test
    public void interpretExpression_callBuiltinOneArg() throws Exception {
        Interpreter interpreter = new Interpreter();
        interpreter.addBinding("length",
                new BuiltinFunctionValue(args -> new NumberValue(args.get(0).requireString().getValue().length()), OptionalInt.of(1)));
        assertEquals(new NumberValue(5), interpreter.interpretExpression("length(\"Hello\")"));
    }

    @Test
    public void interpretExpression_callBuiltinTwoArgs() throws Exception {
        Interpreter interpreter = new Interpreter();
        interpreter.addBinding("startsWith",
                new BuiltinFunctionValue(args -> BoolValue.of(args.get(1).requireString().getValue().startsWith(args.get(0).requireString().getValue())), OptionalInt.of(2)));
        assertEquals(BoolValue.True, interpreter.interpretExpression("startsWith(\"foo\", \"foobar\")"));
        assertEquals(BoolValue.False, interpreter.interpretExpression("startsWith(\"notfoo\", \"foobar\")"));
    }

    @Test
    public void interpretExpression_simpleLambda() throws Exception {
        assertEquals(new NumberValue(10), new Interpreter().interpretExpression("(x => x)(10)"));
    }

    @Test
    public void interpretExpression_binaryLambda() throws Exception {
        assertEquals(new NumberValue(10), new Interpreter().interpretExpression("((x, y) => y)(5, 10)"));
    }

    @Test
    public void interpretExpression_closure() throws Exception {
        assertEquals(new NumberValue(10), new Interpreter().interpretExpression("{\n" +
                "  var a = 4;\n" +
                "  x => a + x;\n" +
                "}(6)"));
    }

    @Test
    public void interpretExpression_mutableClosure() throws Exception {
        Interpreter interpreter = new Interpreter();
        interpreter.interpretExpression("var setAndGet = {\n" +
                "  var i = 0;\n" +
                "  x => {\n" +
                "    var r = i;\n" +
                "    i = x;\n" +
                "    r;  \n" +
                "  };\n" +
                "}");
        assertEquals(new NumberValue(0), interpreter.interpretExpression("setAndGet(10)"));
        assertEquals(new NumberValue(10), interpreter.interpretExpression("setAndGet(20)"));
    }

    @Test
    public void interpretExpression_nullaryClosure() throws Exception {
        Interpreter interpreter = new Interpreter();
        interpreter.interpretExpression("var times = (n, f) => {\n" +
                "  var i = 0;\n" +
                "  while(i < n) {\n" +
                "    f();\n" +
                "    i = i + 1;\n" +
                "  };\n" +
                "}");
        assertEquals(new NumberValue(16), interpreter.interpretExpression("{\n" +
                "  var x = 1;\n" +
                "  times(4, => x = x * 2);\n" +
                "  x;\n" +
                "}"));
    }

    @Test
    public void interpretExpression_stringLength() throws Exception {
        assertEquals(BoolValue.True, new Interpreter().interpretExpression("\"Hello, world!\".length == 13"));
    }

    @Test
    public void interpretExpression_numberClamp() throws Exception {
        assertEquals(new NumberValue(10), new Interpreter().interpretExpression("50.clamp(3, 10)"));
    }
}
