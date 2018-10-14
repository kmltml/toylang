package com.github.kmltml.toylang.parsing;

import java.util.HashMap;
import java.util.Map;

/**
 * An infix operator.
 */
public enum InfixOp {

    Plus("+", 6, true), Minus("-", 6, true), Times("*", 7, true), Div("/", 7, true), Mod("%", 7, true),
    Or("||", 2, true), And("&&", 3, true), Assign("=", 1, false), Exp("**", 8, false), Eq("==", 4, true),
    Neq("!=", 4, true), Less("<", 5, true), Greater(">", 5, true), LessEq("<=", 5, true),
    GreaterEq(">=", 5, true), Dot(".", 11, true);

    private final String name;
    private final int precedence;
    private final boolean leftAssoc;

    private static Map<String, InfixOp> opsByName = new HashMap<>();
    static {
        for (InfixOp op : values()) {
            opsByName.put(op.name, op);
        }
    }

    InfixOp(String name, int precedence, boolean leftAssoc) {
        this.name = name;
        this.precedence = precedence;
        this.leftAssoc = leftAssoc;
    }

    /**
     * Finds an infix operator, given its name.
     * @param src The name to find.
     * @return The infix operator, null if it doesn't exist.
     */
    public static InfixOp fromSource(String src) {
        return opsByName.get(src);
    }

    /**
     * Checks if there exists an infix operator with given name.
     * @param src The name to check.
     * @return true, if there exists an infix operator with given name, false otherwise.
     */
    public static boolean isOperator(String src) {
        return opsByName.containsKey(src);
    }

    /**
     * The name of the operator, as it appears in source code.
     */
    public String getName() {
        return name;
    }

    /**
     * The precedence of the operator, higher precedence binds more strongly.
     */
    public int getPrecedence() {
        return precedence;
    }

    /**
     * If true, operators of the same precedence are evaluated from left to right, if false then the other way around.
     */
    public boolean isLeftAssoc() {
        return leftAssoc;
    }
}
