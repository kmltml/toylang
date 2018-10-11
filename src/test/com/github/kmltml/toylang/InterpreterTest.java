package com.github.kmltml.toylang;

import com.github.kmltml.toylang.runtime.value.BoolValue;
import com.github.kmltml.toylang.runtime.value.NumberValue;
import com.github.kmltml.toylang.runtime.value.StringValue;
import com.github.kmltml.toylang.runtime.value.UnitValue;
import org.junit.Test;

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
}
