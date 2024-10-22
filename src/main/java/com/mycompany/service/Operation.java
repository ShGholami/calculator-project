package com.mycompany.service;

import java.util.function.DoubleBinaryOperator;
import java.util.function.IntBinaryOperator;

public enum Operation {
    PLUS("+", (a, b) -> a + b, (a, b) -> a + b),
    MINUS("-", (a, b) -> a - b, (a, b) -> a - b),
    TIMES("*", (a, b) -> a * b, (a, b) -> a * b),
    DIVIDE("/", (a, b) -> a / b, (a, b) -> a / b);

    private final String symbol;
    private final DoubleBinaryOperator doubleOp;
    private final IntBinaryOperator intOp;

    Operation(String symbol, DoubleBinaryOperator doubleOp, IntBinaryOperator intOp) {
        this.symbol = symbol;
        this.doubleOp = doubleOp;
        this.intOp = intOp;
    }

    public double apply(double a, double b) {
        return doubleOp.applyAsDouble(a, b);
    }

    public int apply(int a, int b) {
        return intOp.applyAsInt(a, b);
    }

    public String getSymbol() {
        return symbol;
    }

    @Override
    public String toString() {
        return symbol;
    }
}
