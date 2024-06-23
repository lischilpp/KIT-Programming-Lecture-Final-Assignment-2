package edu.kit.informatik.ui.exceptions;

import edu.kit.informatik.ui.strings.ExceptionMessage;

/**
 * An exception thrown when the instruction part of a command entered by the user is invalid.
 *
 * @author Linus Schilpp
 * @version 1.0
 */
public class InvalidInstructionException extends InputException {
    /**
     * Instantiates a new InvalidInstructionException.
     *
     * @param instruction the instruction
     */
    public InvalidInstructionException(String instruction) {
        super(String.format(ExceptionMessage.INVALID_INSTRUCTION.toString(), instruction));
    }
}
