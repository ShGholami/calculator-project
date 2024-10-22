package com.mycompany.service;

import com.mycompany.exception.DivisionByZeroException;
import com.mycompany.exception.InvalidOperationException;
import com.mycompany.util.ValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Scanner;

public class Calculator {

    private static final Logger LOGGER = LoggerFactory.getLogger(Calculator.class);

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                String num1Input = getValidatedInput(scanner, "Enter the first number: ");

                String inputSymbol = getOperationInput(scanner);

                if (inputSymbol.equalsIgnoreCase("exit")) {
                    System.out.println("Exiting the calculator. Goodbye!");
                    break;
                }

                Optional<Operation> optionalOperation = getOperationFromSymbol(inputSymbol);
                Operation operation = optionalOperation.orElseThrow(() -> new InvalidOperationException(inputSymbol));


                String num2Input = getValidatedInput(scanner, "Enter the second number: ");

                if (operation == Operation.DIVIDE && Double.parseDouble(num2Input) == 0) {
                    throw new DivisionByZeroException();
                }

                performCalculation(operation, num1Input, num2Input, inputSymbol);

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

    private static Optional<Operation> getOperationFromSymbol(String symbol) {
        for (Operation op : Operation.values()) {
            if (op.getSymbol().equals(symbol)) {
                return Optional.of(op);
            }
        }
        return Optional.empty();
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

    private static BigDecimal performCalculation(Operation operation, String num1Input, String num2Input, String operationSymbol) {
        BigDecimal num1 = new BigDecimal(num1Input);
        BigDecimal num2 = new BigDecimal(num2Input);
        BigDecimal result = operation.apply(num1, num2);
        System.out.printf("Result of %s %s %s = %s%n", num1.toPlainString(), operationSymbol, num2.toPlainString(), result.toPlainString());
        LOGGER.info("Operation: {} {} {} = {}", num1.toPlainString(), operationSymbol, num2.toPlainString(), result.toPlainString());
        return result;
    }


}
