package com.mycompany.service;

import com.mycompany.exception.DivisionByZeroException;

import java.math.BigDecimal;
import java.util.Optional;

public class Calculator {

    public Optional<Operation> getOperationFromSymbol(String symbol) {
        for (Operation op : Operation.values()) {
            if (op.getSymbol().equals(symbol)) {
                return Optional.of(op);
            }
        }
        return Optional.empty();
    }

    public void checkDivideByZero(Operation operation, String num2Input) throws DivisionByZeroException {
        if (operation == Operation.DIVIDE && new BigDecimal(num2Input).compareTo(BigDecimal.ZERO) == 0) {
            throw new DivisionByZeroException();
        }
    }

    public BigDecimal performCalculation(Operation operation, String num1Input, String num2Input) {
        BigDecimal num1 = new BigDecimal(num1Input);
        BigDecimal num2 = new BigDecimal(num2Input);
        return operation.apply(num1, num2);
    }
}
