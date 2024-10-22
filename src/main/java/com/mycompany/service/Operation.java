package com.mycompany.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.function.BiFunction;

public enum Operation {
    PLUS("+", BigDecimal::add),
    MINUS("-", BigDecimal::subtract),
    TIMES("*", BigDecimal::multiply),
    DIVIDE("/", (x, y) -> x.divide(y, 10, RoundingMode.HALF_UP));

    private final String symbol;
    private final BiFunction<BigDecimal, BigDecimal, BigDecimal> bigDecimalOperation;


    Operation(String symbol, BiFunction<BigDecimal, BigDecimal, BigDecimal> bigDecimalOperation) {
        this.symbol = symbol;
        this.bigDecimalOperation = bigDecimalOperation;
    }

    public BigDecimal apply(BigDecimal x, BigDecimal y) {
        return bigDecimalOperation.apply(x, y);
    }

    public String getSymbol() {
        return symbol;
    }

    @Override
    public String toString() {
        return symbol;
    }
}
