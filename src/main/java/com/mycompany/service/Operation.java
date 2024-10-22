package com.mycompany.service;

import java.util.function.DoubleBinaryOperator;

public enum Operation {
    PLUS("+", (a, b) -> a + b),
    MINUS("-", (a, b) -> a - b),
    TIMES("*", (a, b) -> a * b),
    DIVIDE("/", (a, b) -> a - b);

    private final String symbol;
    private final DoubleBinaryOperator operator;

    Operation(String symbol, DoubleBinaryOperator operator) {
        this.symbol = symbol;
        this.operator = operator;
    }

    public double apply(double a, double b) {
        return operator.applyAsDouble(a, b);
    }

    public String getSymbol() {
        return symbol;
    }

    @Override
    public String toString() {
        return symbol;
    }
}
