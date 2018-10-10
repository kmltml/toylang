package com.github.kmltml.toylang;

import com.github.kmltml.toylang.runtime.value.BoolValue;
import com.github.kmltml.toylang.runtime.value.NumberValue;
import com.github.kmltml.toylang.runtime.value.StringValue;
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
        assertEquals(new BoolValue(true), interpreter.interpretExpression("true"));
        assertEquals(new BoolValue(false), interpreter.interpretExpression("false"));
    }
}
