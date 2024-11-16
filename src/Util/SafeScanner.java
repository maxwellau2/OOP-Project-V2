package Util;


import java.util.InputMismatchException;
import java.util.List;
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

        // Clear the buffer after reading an integer to handle any lingering newline
        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }

        return input;
    }

    public static String getValidatedStringInput(Scanner scanner, String prompt, int maxLength) {
        String input = "";
        boolean valid = false;

        while (!valid) {
            System.out.print(prompt);
            if (input.isEmpty())  input = scanner.nextLine().trim(); // Get the line and trim spaces

            if (input.isEmpty()) {
                System.out.println("Invalid input. Please enter a non-empty string.");
            } else if (input.length() > maxLength) {
                System.out.println("Input is too long. Maximum length allowed is " + maxLength + " characters.");
            } else {
                valid = true;
            }
        }

        return input;
    }

    public static String getValidatedStringInput(Scanner scanner, String prompt, List<String> validInputs) {
        String input = "";
        boolean valid = false;

        while (!valid) {
            System.out.print(prompt);
            input = scanner.nextLine().trim(); // Get the line and trim spaces

            if (input.isEmpty()) {
                System.out.println("Invalid input. Please enter a non-empty string.");
            } else if (!validInputs.contains(input)) {
                System.out.println("Invalid input. Accepted values are: " + validInputs);
            } else {
                valid = true;
            }
        }

        return input;
    }

    public static String getStrongPassword(Scanner scanner, String prompt) {
        String password = "";
        boolean valid = false;

        while (!valid) {
            System.out.print(prompt);
            password = readPasswordMasked(scanner); // Read and mask password input
            if (validatePassword(password)) {
                valid = true;
            }
        }

        return password;
    }

    private static boolean validatePassword(String password) {
        if (password.length() < 8) {
            System.out.println("Password must be at least 8 characters long.");
            return false;
        } else if (!password.matches(".*[A-Z].*")) {
            System.out.println("Password must contain at least one uppercase letter.");
            return false;
        } else if (!password.matches(".*[a-z].*")) {
            System.out.println("Password must contain at least one lowercase letter.");
            return false;
        } else if (!password.matches(".*\\d.*")) {
            System.out.println("Password must contain at least one digit.");
            return false;
        } else if (!password.matches(".*[!@#$%^&*(),.?\":{}|<>].*")) {
            System.out.println("Password must contain at least one special character (e.g., !@#$%^&*).");
            return false;
        }
        return true;
    }

    public static String readPasswordMasked(Scanner scanner) {
        StringBuilder password = new StringBuilder();
        try {
            while (true) {
                char ch = (char) System.in.read(); // Read one character at a time
                if (ch == '\n') break; // Enter key signals end of input
                if (ch == '\b' && password.length() > 0) {
                    password.deleteCharAt(password.length() - 1); // Handle backspace
                    System.out.print("\b \b"); // Erase character from console
                } else if (ch != '\b') {
                    password.append(ch);
                    System.out.print("*"); // Print `*` for each character
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(); // Move to the next line
        return password.toString().trim();
    }
    public static String readPasswordMasked(Scanner scanner, String prompt) {
        System.out.print(prompt);

        StringBuilder password = new StringBuilder();
        try {
            while (true) {
                char ch = (char) System.in.read(); // Read one character at a time
                if (ch == '\n') break; // Enter key signals end of input
                if (ch == '\b' && password.length() > 0) {
                    password.deleteCharAt(password.length() - 1); // Handle backspace
                    System.out.print("\b \b"); // Erase character from console
                } else if (ch != '\b') {
                    password.append(ch);
                    System.out.print("*"); // Print `*` for each character
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(); // Move to the next line
        return password.toString().trim();
    }
}
