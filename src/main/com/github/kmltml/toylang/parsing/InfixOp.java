package com.github.kmltml.toylang.parsing;

import java.util.HashMap;
import java.util.Map;

public enum InfixOp {

    Plus("+", 6, true), Minus("-", 6, true), Times("*", 7, true), Div("/", 7, true), Mod("%", 7, true), Or("||", 2, true), And("&&", 3, true), Assign("=", 1, false),
    Exp("**", 8, false), Eq("==", 4, true), Neq("!=", 4, true), Less("<", 5, true), Greater(">", 5, true), LessEq("<=", 5, true), GreaterEq(">=", 5, true);

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

    public static InfixOp fromSource(String src) {
        return opsByName.get(src);
    }

    public static boolean isOperator(String src) {
        return opsByName.containsKey(src);
    }

    public String getName() {
        return name;
    }

    public int getPrecedence() {
        return precedence;
    }

    public boolean isLeftAssoc() {
        return leftAssoc;
    }
}
