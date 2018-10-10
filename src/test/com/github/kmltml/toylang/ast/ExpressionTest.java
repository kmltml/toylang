package com.github.kmltml.toylang.ast;

import com.github.kmltml.toylang.runtime.value.StringValue;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ExpressionTest {

    @Test
    public void evaluate_StringExpression() throws Exception {
        assertEquals(new StringValue("test"), new StringExpression("test").evaluate());
    }
}
