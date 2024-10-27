package com.mycompany;

import com.mycompany.exception.DivisionByZeroException;
import com.mycompany.exception.InvalidOperationException;
import com.mycompany.io.UserInterface;
import com.mycompany.service.Calculator;
import com.mycompany.service.Operation;
import com.mycompany.util.ValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Optional;

public class CalculatorApp {

    private static final Logger LOGGER = LoggerFactory.getLogger(CalculatorApp.class);
    private final Calculator calculator;
    private final UserInterface userInterface;

    public CalculatorApp(Calculator calculator, UserInterface userInterface) {
        this.calculator = calculator;
        this.userInterface = userInterface;
    }

    public void runCalculator() {
        while (true) {
            try {
                String num1Input = userInterface.getInput("Enter the first number: ");
                if (!ValidationUtil.isNumeric(num1Input)) {
                    userInterface.displayOutput("Invalid input. Please enter a valid number.");
                    continue;
                }

                String inputSymbol = userInterface.getInput("Enter operation (+, -, *, /) or type 'exit' to quit: ");
                if (inputSymbol.equalsIgnoreCase("exit")) {
                    userInterface.displayOutput("Exiting the calculator. Goodbye!");
                    break;
                }

                Optional<Operation> optionalOperation = calculator.getOperationFromSymbol(inputSymbol);
                Operation operation = optionalOperation.orElseThrow(() -> new InvalidOperationException(inputSymbol));

                String num2Input = userInterface.getInput("Enter the second number: ");
                if (!ValidationUtil.isNumeric(num1Input)) {
                    userInterface.displayOutput("Invalid input. Please enter a valid number.");
                    continue;
                }

                calculator.checkDivideByZero(operation, num2Input);

                BigDecimal result = calculator.performCalculation(operation, num1Input, num2Input);
                String outputMessage = String.format("Result of %s %s %s = %s", num1Input, inputSymbol, num2Input, result.toPlainString());
                userInterface.displayOutput(outputMessage);
                LOGGER.info("Operation: {} {} {} = {}", num1Input, inputSymbol, num2Input, result.toPlainString());

            } catch (InvalidOperationException | DivisionByZeroException e) {
                LOGGER.error(e.getMessage());
                userInterface.displayOutput(e.getMessage());
            } catch (NumberFormatException e) {
                LOGGER.error("Invalid number format", e);
                userInterface.displayOutput("Please enter valid numbers.");
            } catch (Exception e) {
                LOGGER.error("Unexpected error occurred", e);
                userInterface.displayOutput("An unexpected error occurred. Please try again.");
            }
        }
    }
}
