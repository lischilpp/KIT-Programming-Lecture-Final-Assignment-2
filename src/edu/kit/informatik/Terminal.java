package edu.kit.informatik;

import java.util.Scanner;

/**
 * This Terminal class replaces the original class provided by the assignment.
 * The original class has been removed as it was not authored by the current developer.
 * 
 * This class provides simple terminal input and output functionalities including:
 * - Reading a line from the terminal input.
 * - Printing a line to the terminal output.
 * - Printing an error message to the terminal error output.
 */
public final class Terminal {

    private static Scanner scanner = new Scanner(System.in);

    private Terminal() {
        throw new AssertionError("Utility class should not be instantiated");
    }

    public static String readLine() {
        return scanner.nextLine();
    }

    public static void printLine(String message) {
        System.out.println(message);
    }

    public static void printError(String errorMessage) {
        System.err.println(errorMessage);
    }
}