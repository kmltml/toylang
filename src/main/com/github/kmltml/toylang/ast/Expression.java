package com.github.kmltml.toylang.ast;

import com.github.kmltml.toylang.runtime.Value;

public interface Expression {

    Value evaluate();

}
