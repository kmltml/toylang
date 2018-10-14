package com.github.kmltml.toylang.ast;

import com.github.kmltml.toylang.runtime.EvaluationException;
import com.github.kmltml.toylang.runtime.Scope;
import com.github.kmltml.toylang.runtime.Value;
import com.github.kmltml.toylang.runtime.value.UnitValue;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Represents a block expression delimited in source by braces and containing (possibly zero) expressions, which
 * are evaluated sequentially. The yielded value is that of the last evaluated expression or Unit, if there are none.
 * The block expression also introduces a new scope, which means, that any variables defined within will not be accessible
 * outside of the block.
 *
 * Example:
 * <code>
 *     var x = 10;
 *     {
 *         var x = 20;
 *         x; // -> 20
 *     }
 *     x; // -> 10
 * </code>
 */
public class BlockExpression extends Expression {

    private List<Expression> statements;

    public BlockExpression(List<Expression> statements) {
        this.statements = statements;
    }

    @Override
    public Value<?, ?> evaluate(Scope scope) throws EvaluationException {
        Scope innerScope = new Scope(scope);
        Value<?, ?> lastValue = UnitValue.instance;
        for (Expression statement : statements) {
            lastValue = statement.evaluate(innerScope);
        }
        return lastValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BlockExpression that = (BlockExpression) o;
        return Objects.equals(statements, that.statements);
    }

    @Override
    public int hashCode() {
        return Objects.hash(statements);
    }

    @Override
    public String toString() {
        return String.format("Block(%s)", statements.stream().map(Object::toString).collect(Collectors.joining(", ")));
    }
}
