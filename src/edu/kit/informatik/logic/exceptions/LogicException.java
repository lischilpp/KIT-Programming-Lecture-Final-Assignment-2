package edu.kit.informatik.logic.exceptions;

/**
 * A general exception when something violates the logic of the program.
 *
 * @author Linus Schilpp
 * @version 1.0
 */
public class LogicException extends Exception {

    /**
     * Instantiates a new LogicException.
     *
     * @param message the exception message
     */
    public LogicException(String message) {
        super(message);
    }
}
