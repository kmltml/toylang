package com.github.kmltml.toylang.parsing;

import java.util.HashSet;
import java.util.Set;

public enum Keyword {

    True, False, If, Else, Var;

    private static Set<String> keywordNames = new HashSet<>();
    static {
        for (Keyword k : values()) {
            keywordNames.add(k.getName());
        }
    }

    public String getName() {
        return name().toLowerCase();
    }

    public static Keyword forSource(String s) {
        return valueOf(Character.toUpperCase(s.charAt(0)) + s.substring(1).toLowerCase());
    }

    public static boolean isKeyword(String s) {
        return keywordNames.contains(s.toLowerCase());
    }

}
