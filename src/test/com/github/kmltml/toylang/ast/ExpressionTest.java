package com.github.kmltml.toylang.ast;

import com.github.kmltml.toylang.parsing.InfixOp;
import com.github.kmltml.toylang.parsing.PrefixOp;
import com.github.kmltml.toylang.runtime.Scope;
import com.github.kmltml.toylang.runtime.value.*;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.OptionalInt;

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

    @Test
    public void evaluate_prefixNumberPositive() throws Exception {
        assertEquals(new NumberValue(42), new PrefixExpression(PrefixOp.Positive, new NumberExpression(42)).evaluate(new Scope()));
    }

    @Test
    public void evaluate_prefixNumberNegative() throws Exception {
        assertEquals(new NumberValue(-42), new PrefixExpression(PrefixOp.Negative, new NumberExpression(42)).evaluate(new Scope()));
    }

    @Test
    public void evaluate_prefixNumberBitNegation() throws Exception {
        assertEquals(new NumberValue(~42), new PrefixExpression(PrefixOp.BitNegation, new NumberExpression(42)).evaluate(new Scope()));
    }

    @Test
    public void evaluate_prefixBooleanNegation() throws Exception {
        assertEquals(BoolValue.True, new PrefixExpression(PrefixOp.Not, new BoolExpression(false)).evaluate(new Scope()));
        assertEquals(BoolValue.False, new PrefixExpression(PrefixOp.Not, new BoolExpression(true)).evaluate(new Scope()));
    }

    @Test
    public void evaluate_ifThenElse() throws Exception {
        assertEquals(new NumberValue(1),
                new IfExpression(new BoolExpression(true), new NumberExpression(1), new NumberExpression(0)).evaluate(new Scope()));
        assertEquals(new NumberValue(0),
                new IfExpression(new BoolExpression(false), new NumberExpression(1), new NumberExpression(0)).evaluate(new Scope()));
    }

    @Test
    public void evaluate_if() throws Exception {
        Scope scope = new Scope();
        scope.putValue("foo", new NumberValue(0));
        assertEquals(UnitValue.instance, new IfExpression(new BoolExpression(false),
                new InfixExpression(InfixOp.Assign, new VarExpression("foo"), new NumberExpression(1)), null).evaluate(scope));
        assertEquals(new NumberValue(0), scope.getValue("foo").get());
    }

    @Test
    public void evaluate_block() throws Exception {
        Scope scope = new Scope();
        scope.putValue("foo", new NumberValue(10));
        assertEquals(new NumberValue(20), new BlockExpression(Arrays.asList(
                new InfixExpression(InfixOp.Assign, new VarExpression("foo"), new InfixExpression(InfixOp.Plus, new VarExpression("foo"), new NumberExpression(10))),
                new VarExpression("foo")
        )).evaluate(scope));
        assertEquals(new NumberValue(20), scope.getValue("foo").get());
    }

    @Test
    public void evaluate_emptyBlock() throws Exception {
        assertEquals(UnitValue.instance, new BlockExpression(Collections.emptyList()).evaluate(new Scope()));
    }

    @Test
    public void evaluate_varDef() throws Exception {
        Scope scope = new Scope();
        assertEquals(UnitValue.instance, new VarDefExpression("foo", new NumberExpression(42)).evaluate(scope));
        assertEquals(new NumberValue(42), scope.getValue("foo").get());
    }

    @Test
    public void evaluate_while() throws Exception {
        Scope scope = new Scope();
        scope.putValue("x", new NumberValue(0));
        assertEquals(UnitValue.instance, new WhileExpression(
                new InfixExpression(InfixOp.Less, new VarExpression("x"), new NumberExpression(10)),
                new InfixExpression(InfixOp.Assign, new VarExpression("x"), new InfixExpression(InfixOp.Plus, new VarExpression("x"), new NumberExpression(1)))
        ).evaluate(scope));
        assertEquals(new NumberValue(10), scope.getValue("x").get());
    }

    @Test
    public void evaluate_callBuiltin() throws Exception {
        Scope scope = new Scope();
        scope.putValue("foo", new BuiltinFunctionValue(args -> args.get(1), OptionalInt.of(2)));
        assertEquals(new NumberValue(2), new CallExpression(new VarExpression("foo"),
                Arrays.asList(new StringExpression("bar"), new NumberExpression(2))).evaluate(scope));
    }

    @Test
    public void evaluate_lambda() throws Exception {
        Scope scope = new Scope();
        List<String> argNames = Arrays.asList("x", "y");
        VarExpression body = new VarExpression("y");
        assertEquals(new LambdaValue(scope, argNames, body),
                new LambdaExpression(argNames, body).evaluate(scope));
    }

    @Test
    public void evaluate_callLambda() throws Exception {
        assertEquals(new NumberValue(1),
                new CallExpression(
                        new LambdaExpression(Collections.singletonList("x"), new VarExpression("x")),
                        Collections.singletonList(new NumberExpression(1))
                ).evaluate(new Scope()));
    }

    @Test
    public void evaluate_methodNumberAbs() throws Exception {
        assertEquals(new NumberValue(42),
                new MethodExpression(new NumberExpression(-42), "abs").evaluate(new Scope()));
    }

    @Test
    public void evaluate_methodNumberClamp() throws Exception {
        assertEquals(new NumberValue("0.2"),
                new CallExpression(new MethodExpression(new NumberExpression("0.2"), "clamp"),
                        Arrays.asList(new NumberExpression("0.0"), new NumberExpression("1.0"))).evaluate(new Scope()));
        assertEquals(new NumberValue("0.0"),
                new CallExpression(new MethodExpression(new NumberExpression("-0.1"), "clamp"),
                        Arrays.asList(new NumberExpression("0.0"), new NumberExpression("1.0"))).evaluate(new Scope()));
        assertEquals(new NumberValue("1.0"),
                new CallExpression(new MethodExpression(new NumberExpression("1.2"), "clamp"),
                        Arrays.asList(new NumberExpression("0.0"), new NumberExpression("1.0"))).evaluate(new Scope()));
    }

    @Test
    public void evaluate_methodStringLength() throws Exception {
        assertEquals(new NumberValue(6),
                new MethodExpression(new StringExpression("foobar"), "length").evaluate(new Scope()));
    }
}
