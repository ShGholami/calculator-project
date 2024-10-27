package com.mycompany;

import com.mycompany.io.ConsoleUserInterface;
import com.mycompany.io.UserInterface;
import com.mycompany.service.Calculator;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        UserInterface userInterface = new ConsoleUserInterface(new Scanner(System.in));
        CalculatorApp app = new CalculatorApp(calculator, userInterface);
        app.runCalculator();
    }
}
