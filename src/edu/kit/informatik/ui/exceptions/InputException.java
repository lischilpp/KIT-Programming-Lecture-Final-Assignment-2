package edu.kit.informatik.ui.exceptions;

/**
 * An general Input exception thrown when the user enters invalid commands.
 *
 * @author Linus Schilpp
 * @version 1.0
 */
public abstract class InputException extends Exception {
    /**
     * Instantiates a new InputException.
     *
     * @param msg the error message
     */
    public InputException(String msg) {
        super(msg);
    }
}
