package com.github.kmltml.toylang.ast;

import com.github.kmltml.toylang.parsing.InfixOp;
import com.github.kmltml.toylang.runtime.Scope;
import com.github.kmltml.toylang.runtime.value.BoolValue;
import com.github.kmltml.toylang.runtime.value.NumberValue;
import com.github.kmltml.toylang.runtime.value.StringValue;
import com.github.kmltml.toylang.runtime.value.UnitValue;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class ExpressionTest {

    @Test
    public void evaluate_StringExpression() throws Exception {
        assertEquals(new StringValue("test"), new StringExpression("test").evaluate(new Scope()));
    }

    @Test
    public void evaluate_NumberExpression() throws Exception {
        assertEquals(new NumberValue(42), new NumberExpression(new BigDecimal(42)).evaluate(new Scope()));
    }

    @Test
    public void evaluate_VarExpression() throws Exception {
        Scope scope = new Scope();
        scope.putValue("foo", new NumberValue(42));
        assertEquals(new NumberValue(42), new VarExpression("foo").evaluate(scope));
    }

    @Test
    public void evaluate_BoolExpression() throws Exception {
        assertEquals(BoolValue.of(true), new BoolExpression(true).evaluate(new Scope()));
    }

    @Test
    public void evaluate_infixNumberPlus() throws Exception {
        assertEquals(new NumberValue(10), new InfixExpression(InfixOp.Plus, new NumberExpression(4), new NumberExpression(6)).evaluate(new Scope()));
    }

    @Test
    public void evaluate_infixNumberMinus() throws Exception {
        assertEquals(new NumberValue(-2), new InfixExpression(InfixOp.Minus, new NumberExpression(4), new NumberExpression(6)).evaluate(new Scope()));
    }

    @Test
    public void evaluate_infixNumberTimes() throws Exception {
        assertEquals(new NumberValue(24), new InfixExpression(InfixOp.Times, new NumberExpression(4), new NumberExpression(6)).evaluate(new Scope()));
    }

    @Test
    public void evaluate_infixNumberDivide() throws Exception {
        assertEquals(new NumberValue(2), new InfixExpression(InfixOp.Div, new NumberExpression(10), new NumberExpression(5)).evaluate(new Scope()));
    }

    @Test
    public void evaluate_infixNumberMod() throws Exception {
        assertEquals(new NumberValue(1), new InfixExpression(InfixOp.Mod, new NumberExpression(5), new NumberExpression(4)).evaluate(new Scope()));
    }

    @Test
    public void evaluate_infixNumberOr() throws Exception {
        assertEquals(new NumberValue(7), new InfixExpression(InfixOp.Or, new NumberExpression(3), new NumberExpression(6)).evaluate(new Scope()));
    }

    @Test
    public void evaluate_infixNumberAnd() throws Exception {
        assertEquals(new NumberValue(2), new InfixExpression(InfixOp.And, new NumberExpression(3), new NumberExpression(6)).evaluate(new Scope()));
    }

    @Test
    public void evaluate_infixNumberExp() throws Exception {
        assertEquals(new NumberValue(65536), new InfixExpression(InfixOp.Exp, new NumberExpression(2), new NumberExpression(16)).evaluate(new Scope()));
    }

    @Test
    public void evaluate_infixNumberEq() throws Exception {
        assertEquals(BoolValue.True, new InfixExpression(InfixOp.Eq, new NumberExpression(42), new NumberExpression(42)).evaluate(new Scope()));
        assertEquals(BoolValue.False, new InfixExpression(InfixOp.Eq, new NumberExpression(42), new NumberExpression(40)).evaluate(new Scope()));
    }

    @Test
    public void evaluate_infixNumberNeq() throws Exception {
        assertEquals(BoolValue.False, new InfixExpression(InfixOp.Neq, new NumberExpression(42), new NumberExpression(42)).evaluate(new Scope()));
        assertEquals(BoolValue.True, new InfixExpression(InfixOp.Neq, new NumberExpression(42), new NumberExpression(40)).evaluate(new Scope()));
    }

    @Test
    public void evaluate_infixNumberLess() throws Exception {
        assertEquals(BoolValue.True, new InfixExpression(InfixOp.Less, new NumberExpression(2), new NumberExpression(16)).evaluate(new Scope()));
        assertEquals(BoolValue.False, new InfixExpression(InfixOp.Less, new NumberExpression(16), new NumberExpression(16)).evaluate(new Scope()));
        assertEquals(BoolValue.False, new InfixExpression(InfixOp.Less, new NumberExpression(32), new NumberExpression(16)).evaluate(new Scope()));
    }

    @Test
    public void evaluate_infixNumberLessEq() throws Exception {
        assertEquals(BoolValue.True, new InfixExpression(InfixOp.LessEq, new NumberExpression(2), new NumberExpression(16)).evaluate(new Scope()));
        assertEquals(BoolValue.True, new InfixExpression(InfixOp.LessEq, new NumberExpression(16), new NumberExpression(16)).evaluate(new Scope()));
        assertEquals(BoolValue.False, new InfixExpression(InfixOp.LessEq, new NumberExpression(32), new NumberExpression(16)).evaluate(new Scope()));
    }

    @Test
    public void evaluate_infixNumberGreater() throws Exception {
        assertEquals(BoolValue.False, new InfixExpression(InfixOp.Greater, new NumberExpression(2), new NumberExpression(16)).evaluate(new Scope()));
        assertEquals(BoolValue.False, new InfixExpression(InfixOp.Greater, new NumberExpression(16), new NumberExpression(16)).evaluate(new Scope()));
        assertEquals(BoolValue.True, new InfixExpression(InfixOp.Greater, new NumberExpression(32), new NumberExpression(16)).evaluate(new Scope()));
    }

    @Test
    public void evaluate_infixNumberGreaterEq() throws Exception {
        assertEquals(BoolValue.False, new InfixExpression(InfixOp.GreaterEq, new NumberExpression(2), new NumberExpression(16)).evaluate(new Scope()));
        assertEquals(BoolValue.True, new InfixExpression(InfixOp.GreaterEq, new NumberExpression(16), new NumberExpression(16)).evaluate(new Scope()));
        assertEquals(BoolValue.True, new InfixExpression(InfixOp.GreaterEq, new NumberExpression(32), new NumberExpression(16)).evaluate(new Scope()));
    }

    @Test
    public void evaluate_infixStringPlus() throws Exception {
        assertEquals(new StringValue("Hello"), new InfixExpression(InfixOp.Plus, new StringExpression("Hel"), new StringExpression("lo")).evaluate(new Scope()));
    }

    @Test
    public void evaluate_infixStringTimes() throws Exception {
        assertEquals(new StringValue("nanananana"), new InfixExpression(InfixOp.Times, new StringExpression("na"), new NumberExpression(5)).evaluate(new Scope()));
    }

    @Test
    public void evaluate_infixString() throws Exception {
        assertEquals(new StringValue("nanananana"), new InfixExpression(InfixOp.Times, new StringExpression("na"), new NumberExpression(5)).evaluate(new Scope()));
    }

    @Test
    public void evaluate_infixStringEq() throws Exception {
        assertEquals(BoolValue.True, new InfixExpression(InfixOp.Eq, new StringExpression("foo"), new StringExpression("foo")).evaluate(new Scope()));
        assertEquals(BoolValue.False, new InfixExpression(InfixOp.Eq, new StringExpression("foo"), new StringExpression("bar")).evaluate(new Scope()));
    }

    @Test
    public void evaluate_infixStringNeq() throws Exception {
        assertEquals(BoolValue.False, new InfixExpression(InfixOp.Neq, new StringExpression("foo"), new StringExpression("foo")).evaluate(new Scope()));
        assertEquals(BoolValue.True, new InfixExpression(InfixOp.Neq, new StringExpression("foo"), new StringExpression("bar")).evaluate(new Scope()));
    }


    @Test
    public void evaluate_infixBoolOr() throws Exception {
        assertEquals(BoolValue.True, new InfixExpression(InfixOp.Or, new BoolExpression(true), new BoolExpression(false)).evaluate(new Scope()));
        assertEquals(BoolValue.False, new InfixExpression(InfixOp.Or, new BoolExpression(false), new BoolExpression(false)).evaluate(new Scope()));
    }

    @Test
    public void evaluate_infixBoolAnd() throws Exception {
        assertEquals(BoolValue.True, new InfixExpression(InfixOp.And, new BoolExpression(true), new BoolExpression(true)).evaluate(new Scope()));
        assertEquals(BoolValue.False, new InfixExpression(InfixOp.And, new BoolExpression(false), new BoolExpression(true)).evaluate(new Scope()));
    }

    @Test
    public void evaluate_infixBoolEq() throws Exception {
        assertEquals(BoolValue.True, new InfixExpression(InfixOp.Eq, new BoolExpression(true), new BoolExpression(true)).evaluate(new Scope()));
        assertEquals(BoolValue.False, new InfixExpression(InfixOp.Eq, new BoolExpression(true), new BoolExpression(false)).evaluate(new Scope()));
    }

    @Test
    public void evaluate_infixBoolNeq() throws Exception {
        assertEquals(BoolValue.False, new InfixExpression(InfixOp.Neq, new BoolExpression(true), new BoolExpression(true)).evaluate(new Scope()));
        assertEquals(BoolValue.True, new InfixExpression(InfixOp.Neq, new BoolExpression(true), new BoolExpression(false)).evaluate(new Scope()));
    }


    @Test
    public void evaluate_infixAssign() throws Exception {
        Scope scope = new Scope();
        scope.putValue("foo", new NumberValue(10));
        assertEquals(UnitValue.instance, new InfixExpression(InfixOp.Assign, new VarExpression("foo"), new NumberExpression(20)).evaluate(scope));
        assertEquals(new NumberValue(20), scope.getValue("foo").get());
    }
}
