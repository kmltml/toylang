package com.github.kmltml.toylang.parsing;

import java.util.HashMap;
import java.util.Map;

/**
 * A prefix operator.
 */
public enum PrefixOp {

    Not("!", 9), BitNegation("~", 9), Negative("-", 6), Positive("+", 6);

    private final String name;
    private final int precedence;

    private static Map<String, PrefixOp> opsByName = new HashMap<>();
    static {
        for (PrefixOp op : values()) {
            opsByName.put(op.name, op);
        }
    }

    PrefixOp(String name, int precedence) {
        this.name = name;
        this.precedence = precedence;
    }

    /**
     * The name of the operator, as it appears in source code.
     */
    public String getName() {
        return name;
    }

    /**
     * The precedence of this operator, dictating at which precedence the expression following it is parsed.
     * Higher precedence binds more strongly.
     */
    public int getPrecedence() {
        return precedence;
    }

    /**
     * Checks if there exists a prefix operator with given name.
     * @param source The name to check.
     * @return true, if there exists a prefix operator with given name, false otherwise.
     */
    public static boolean isPrefixOp(String source) {
        return opsByName.containsKey(source);
    }

    /**
     * Finds a prefix operator, given its name.
     * @param source The name to find.
     * @return The prefix operator, null if it doesn't exist.
     */
    public static PrefixOp fromSource(String source) {
        return opsByName.get(source);
    }
}
