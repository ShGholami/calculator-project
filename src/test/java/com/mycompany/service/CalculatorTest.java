package com.mycompany.service;

import com.mycompany.exception.DivisionByZeroException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CalculatorTest {

    private Calculator calculator;

    @BeforeEach
    void setUp(){
        calculator = new Calculator();
    }

    @Test
    void givenGetOperationFromSymbol_whenValidSymbol_thenReturnCorrectOperation(){
        assertEquals(Optional.of(Operation.PLUS), calculator.getOperationFromSymbol("+"));
        assertEquals(Optional.of(Operation.MINUS), calculator.getOperationFromSymbol("-"));
        assertEquals(Optional.of(Operation.TIMES), calculator.getOperationFromSymbol("*"));
        assertEquals(Optional.of(Operation.DIVIDE), calculator.getOperationFromSymbol("/"));
    }

    @Test
    void givenGetOperationFromSymbol_whenInvalidSymbol_thenReturnsEmptyOptional(){
        assertEquals(Optional.empty(), calculator.getOperationFromSymbol("%"));
        assertEquals(Optional.empty(), calculator.getOperationFromSymbol("ggg"));
    }

    @Test
    void givenCheckDivideByZero_whenZeroDivisor_thenThrowException(){
        DivisionByZeroException exception = assertThrows(DivisionByZeroException.class,
                () -> calculator.checkDivideByZero(Operation.DIVIDE, "0"));

        assertEquals("Division by zero is not allowed.", exception.getMessage());
    }

    @Test
    void givenCheckDivideByZero_whenNotDivision_thenNoException(){
        assertDoesNotThrow(() -> calculator.checkDivideByZero(Operation.TIMES, "0"));
    }

    @Test
    void givenCheckDivideByZero_whenNotZeroDivisor_thenNoException() {
        assertDoesNotThrow(() -> calculator.checkDivideByZero(Operation.DIVIDE, "5"));
    }

    @Test
    void givenPerformCalculation_whenAddition_thenReturnsSum() {
        BigDecimal result = calculator.performCalculation(Operation.PLUS, "10", "5");
        assertEquals(new BigDecimal("15"), result);
    }

    @Test
    void givenPerformCalculation_whenSubtract_thenReturnsMinus() {
        BigDecimal result = calculator.performCalculation(Operation.MINUS, "4", "2");
        assertEquals(new BigDecimal("2"), result);
    }

    @Test
    void givenPerformCalculation_whenSubtractWithFloatingPoint_thenReturnsMinus() {
        BigDecimal result = calculator.performCalculation(Operation.MINUS, "4.1", "0.7");
        assertEquals(new BigDecimal("3.4"), result);
    }

    @Test
    void givenPerformCalculation_whenSubtractWithNegativeResult_thenReturnsMinus() {
        BigDecimal result = calculator.performCalculation(Operation.MINUS, "4", "7");
        assertEquals(new BigDecimal("-3"), result);
    }

    @Test
    void givenPerformCalculation_whenSubtractWithFloatingPointAndNegativeResult_thenReturnsMinus() {
        BigDecimal result = calculator.performCalculation(Operation.MINUS, "4.1", "7.5");
        assertEquals(new BigDecimal("-3.4"), result);
    }

    @Test
    void givenPerformCalculation_whenSubtractWithScaleOfTwo_thenReturnsMinus() {
        BigDecimal result = calculator.performCalculation(Operation.MINUS, "0.04", "0.03");
        assertEquals(new BigDecimal("0.01"), result);
    }

    @Test
    void givenPerformCalculation_whenMultiply_thenReturnsTimes() {
        BigDecimal result = calculator.performCalculation(Operation.TIMES, "0.04", "0.03");
        assertEquals(new BigDecimal("0.0012"), result);
    }

    @Test
    void givenPerformCalculation_whenMultiplyLargeNumbers_thenReturnsTimes() {
        BigDecimal result = calculator.performCalculation(Operation.TIMES, "1000000", "1000000");
        assertEquals(new BigDecimal("1000000000000"), result);
    }

    @Test
    void givenPerformCalculation_whenDivideWithInfiniteScale_thenReturnsWithScaleOfTwo() {
        BigDecimal result = calculator.performCalculation(Operation.DIVIDE, "10", "3");
        assertEquals(new BigDecimal("3.33"), result);
    }

    @Test
    void givenPerformCalculation_whenDivide_thenReturnsDivision() {
        BigDecimal result = calculator.performCalculation(Operation.DIVIDE, "10", "5");
        assertEquals(new BigDecimal("2.00"), result);
    }

}