package com.mycompany.service;

import com.mycompany.exception.DivisionByZeroException;
import com.mycompany.exception.InvalidOperationException;
import com.mycompany.util.ValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Scanner;

public class CalculatorApp {

    private static final Logger LOGGER = LoggerFactory.getLogger(CalculatorApp.class);

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Calculator calculator = new Calculator();

        while (true) {
            try {
                String num1Input = getValidatedInput(scanner, "Enter the first number: ");
                String inputSymbol = getOperationInput(scanner);

                if (inputSymbol.equalsIgnoreCase("exit")) {
                    System.out.println("Exiting the calculator. Goodbye!");
                    break;
                }

                Optional<Operation> optionalOperation = calculator.getOperationFromSymbol(inputSymbol);
                Operation operation = optionalOperation.orElseThrow(() -> new InvalidOperationException(inputSymbol));

                String num2Input = getValidatedInput(scanner, "Enter the second number: ");
                calculator.checkDivideByZero(operation, num2Input);

                BigDecimal result = calculator.performCalculation(operation, num1Input, num2Input);
                System.out.printf("Result of %s %s %s = %s%n", num1Input, inputSymbol, num2Input, result.toPlainString());
                LOGGER.info("Operation: {} {} {} = {}", num1Input, inputSymbol, num2Input, result.toPlainString());

            } catch (InvalidOperationException | DivisionByZeroException e) {
                LOGGER.error(e.getMessage());
                System.out.println(e.getMessage());
            } catch (NumberFormatException e) {
                LOGGER.error("Invalid number format", e);
                System.out.println("Please enter valid numbers.");
            } catch (Exception e) {
                LOGGER.error("Unexpected error occurred", e);
                System.out.println("An unexpected error occurred. Please try again.");
            }
        }
    }

    private static String getOperationInput(Scanner scanner) {
        System.out.println("Enter operation (+, -, *, /) or type 'exit' to quit: ");
        return scanner.nextLine();
    }

    private static String getValidatedInput(Scanner scanner, String message) {
        System.out.println(message);
        String input;
        while (true) {
            input = scanner.nextLine();
            if (ValidationUtil.isNumeric(input)) {
                return input;
            } else {
                System.out.println("Invalid input. Please enter a valid number:");
            }
        }
    }
}
