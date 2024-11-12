package Util;

import java.util.InputMismatchException;
import java.util.Scanner;

public class SafeScanner {

    public static int getValidatedIntInput(Scanner scanner, String prompt, int min, int max) {
        int input = -1;
        boolean valid = false;

        while (!valid) {
            System.out.print(prompt);
            try {
                input = scanner.nextInt();

                // Check if the input is within the range
                if (input >= min && input <= max) {
                    valid = true;
                } else {
                    System.out.println("Please enter a number between " + min + " and " + max + ".");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.next(); // Clear the invalid input
            }
        }

        return input;
    }
}
