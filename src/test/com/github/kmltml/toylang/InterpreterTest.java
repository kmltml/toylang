package com.github.kmltml.toylang;

import com.github.kmltml.toylang.runtime.value.StringValue;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class InterpreterTest {

    @Test
    public void interpretExpression_stringLiteral() throws Exception {
        assertEquals(new StringValue("Hello"), new Interpreter().interpretExpression("\"Hello\""));
    }
}
