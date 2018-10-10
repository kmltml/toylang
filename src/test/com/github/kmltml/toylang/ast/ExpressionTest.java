package com.github.kmltml.toylang.ast;

import com.github.kmltml.toylang.runtime.value.NumberValue;
import com.github.kmltml.toylang.runtime.value.StringValue;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class ExpressionTest {

    @Test
    public void evaluate_StringExpression() throws Exception {
        assertEquals(new StringValue("test"), new StringExpression("test").evaluate());
    }

    @Test
    public void evaluate_NumberExpression() throws Exception {
        assertEquals(new NumberValue(42), new NumberExpression(new BigDecimal(42)).evaluate());
    }
}
