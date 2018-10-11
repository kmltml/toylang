package com.github.kmltml.toylang.parsing;

import java.util.HashMap;
import java.util.Map;

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

    public String getName() {
        return name;
    }

    public int getPrecedence() {
        return precedence;
    }

    public static boolean isPrefixOp(String source) {
        return opsByName.containsKey(source);
    }

    public static PrefixOp fromSource(String source) {
        return opsByName.get(source);
    }
}
