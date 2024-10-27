package com.mycompany.io;

import java.util.Scanner;

public class ConsoleUserInterface implements UserInterface{
    private final Scanner scanner;

    public ConsoleUserInterface(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public String getInput(String prompt) {
        System.out.println(prompt);
        return scanner.nextLine();
    }

    @Override
    public void displayOutput(String message) {
        System.out.println(message);
    }

}
