package Util;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;


/**
 * Utility class for safe and validated input handling from the console.
 * Provides methods to ensure robust input validation for integers, strings, and passwords.
 */
public class SafeScanner {

    /**
     * Prompts the user for an integer input within a specified range.
     *
     * @param scanner Scanner instance to read input.
     * @param prompt  The message to prompt the user.
     * @param min     The minimum allowed value.
     * @param max     The maximum allowed value.
     * @return A validated integer input within the range.
     */
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


    /**
     * Prompts the user for a string input with validation for length and illegal characters.
     *
     * @param scanner   Scanner instance to read input.
     * @param prompt    The message to prompt the user.
     * @param maxLength The maximum allowed length of the string.
     * @return A validated string input.
     */
    public static String getValidatedStringInput(Scanner scanner, String prompt, int maxLength) {
        String input = "";
        boolean valid = false;

        while (!valid) {
            System.out.print(prompt);
            input = scanner.nextLine().trim(); // Get the line and trim spaces

            if (input.isEmpty()) {
                System.out.println("Invalid input. Please enter a non-empty string.");
            } else if (input.length() > maxLength) {
                System.out.println("Input is too long. Maximum length allowed is " + maxLength + " characters.");
            } else if (input.contains(",")) {
                System.out.println("Invalid input. Commas are not allowed.");
            } else {
                valid = true;
            }
        }

        return input;
    }


    /**
     * Prompts the user for a string input from a predefined list of valid options.
     *
     * @param scanner     Scanner instance to read input.
     * @param prompt      The message to prompt the user.
     * @param validInputs A list of valid string inputs.
     * @return A validated string input matching one of the valid options.
     */
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
            } else if (input.contains(",")) {
                System.out.println("Invalid input. Commas are not allowed.");
            } else {
                valid = true;
            }
        }

        return input;
    }


    /**
     * Prompts the user to input a strong password.
     *
     * @param scanner Scanner instance to read input.
     * @param prompt  The message to prompt the user.
     * @return A validated strong password.
     */
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


    /**
     * Validates the password against strength criteria.
     *
     * @param password The password to validate.
     * @return True if the password meets all strength criteria; otherwise, false.
     */
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


    /**
     * Reads a password input from the console, masking the input.
     *
     * @param scanner Scanner instance to read input.
     * @return The entered password as a string.
     */
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


    /**
     * Reads a password input from the console with a prompt, masking the input.
     *
     * @param scanner Scanner instance to read input.
     * @param prompt  The message to prompt the user.
     * @return The entered password as a string.
     */
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

    /**
     * Validates an email address format.
     *
     * @param email The email address to validate.
     * @return True if the email is valid, false otherwise.
     */
    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        return email.matches(emailRegex);
    }

    /**
     * Validates a contact number format.
     * Assumes the contact number must be numeric and 8-15 digits long.
     *
     * @param contactNumber The contact number to validate.
     * @return True if the contact number is valid, false otherwise.
     */
    public static boolean isValidContactNumber(String contactNumber) {
        String contactRegex = "^[0-9]{8,15}$";
        return contactNumber.matches(contactRegex);
    }

    /**
     * Prompts the user for a valid email input.
     *
     * @param scanner Scanner instance to read input.
     * @param prompt  The message to prompt the user.
     * @return A validated email address.
     */
    public static String getValidatedEmailInput(Scanner scanner, String prompt) {
        String email = "";
        boolean valid = false;

        while (!valid) {
            System.out.print(prompt);
            email = scanner.nextLine().trim();

            if (isValidEmail(email)) {
                valid = true;
            } else {
                System.out.println("Invalid email format. Please enter a valid email.");
            }
        }

        return email;
    }

    /**
     * Prompts the user for a valid contact number input.
     *
     * @param scanner Scanner instance to read input.
     * @param prompt  The message to prompt the user.
     * @return A validated contact number.
     */
    public static String getValidatedContactNumberInput(Scanner scanner, String prompt) {
        String contactNumber = "";
        boolean valid = false;

        while (!valid) {
            System.out.print(prompt);
            contactNumber = scanner.nextLine().trim();

            if (isValidContactNumber(contactNumber)) {
                valid = true;
            } else {
                System.out.println("Invalid contact number. Please enter a numeric value between 8 and 15 digits.");
            }
        }

        return contactNumber;
    }



}
