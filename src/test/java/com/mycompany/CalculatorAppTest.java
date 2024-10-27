package com.mycompany;

import com.mycompany.exception.DivisionByZeroException;
import com.mycompany.io.UserInterface;
import com.mycompany.service.Calculator;
import com.mycompany.service.Operation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.Mockito.*;

class CalculatorAppTest {

    private CalculatorApp calculatorApp;

    @Mock
    private Calculator calculatorMock;

    @Mock
    private UserInterface userInterfaceMock;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        calculatorApp = new CalculatorApp(calculatorMock, userInterfaceMock);
    }

    @Test
    void whenAdditionOperation(){
        when(userInterfaceMock.getInput("Enter the first number: ")).thenReturn("10");
        when(userInterfaceMock.getInput("Enter operation (+, -, *, /) or type 'exit' to quit: ")).thenReturn("+").thenReturn("exit");
        when(userInterfaceMock.getInput("Enter the second number: ")).thenReturn("5");

        when(calculatorMock.getOperationFromSymbol("+")).thenReturn(Optional.of(Operation.PLUS));
        when(calculatorMock.performCalculation(Operation.PLUS, "10", "5")).thenReturn(new BigDecimal("15"));

        calculatorApp.runCalculator();
        verify(userInterfaceMock).displayOutput("Result of 10 + 5 = 15");
    }

    @Test
    void whenDivisionByZero(){
        when(userInterfaceMock.getInput("Enter the first number: ")).thenReturn("10");
        when(userInterfaceMock.getInput("Enter operation (+, -, *, /) or type 'exit' to quit: ")).thenReturn("/").thenReturn("exit");
        when(userInterfaceMock.getInput("Enter the second number: ")).thenReturn("0");

        when(calculatorMock.getOperationFromSymbol("/")).thenReturn(Optional.of(Operation.DIVIDE));
        doThrow(new DivisionByZeroException()).when(calculatorMock).checkDivideByZero(Operation.DIVIDE, "0");

        calculatorApp.runCalculator();
        verify(userInterfaceMock).displayOutput("Division by zero is not allowed.");
    }

    @Test
    void whenInvalidOperation() {
        when(userInterfaceMock.getInput("Enter the first number: ")).thenReturn("10");
        when(userInterfaceMock.getInput("Enter operation (+, -, *, /) or type 'exit' to quit: ")).thenReturn("%").thenReturn("exit");

        when(calculatorMock.getOperationFromSymbol("%")).thenReturn(Optional.empty());

        calculatorApp.runCalculator();
        verify(userInterfaceMock).displayOutput("Invalid operation: %");
    }

    @Test
    void whenInvalidFirstNumberFormat(){
        when(userInterfaceMock.getInput("Enter the first number: ")).thenReturn("1fd").thenReturn("10");
        when(userInterfaceMock.getInput("Enter operation (+, -, *, /) or type 'exit' to quit: ")).thenReturn("exit");

        calculatorApp.runCalculator();
        verify(userInterfaceMock).displayOutput("Please enter a valid number.");
    }

    @Test
    void whenInvalidSecondNumberFormat(){
        when(userInterfaceMock.getInput("Enter the first number: ")).thenReturn("10");
        when(userInterfaceMock.getInput("Enter operation (+, -, *, /) or type 'exit' to quit: ")).thenReturn("*").thenReturn("exit");
        when(calculatorMock.getOperationFromSymbol("*")).thenReturn(Optional.of(Operation.TIMES));
        when(userInterfaceMock.getInput("Enter the second number: ")).thenReturn("d");
        calculatorApp.runCalculator();
        verify(userInterfaceMock).displayOutput("Please enter a valid number.");
    }


}