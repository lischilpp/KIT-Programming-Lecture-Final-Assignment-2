package edu.kit.informatik.ui.exceptions;

import edu.kit.informatik.ui.strings.ExceptionMessage;

/**
 * An exception thrown when a command that does not have parameters is passed with a parameter
 *
 * @author Linus Schilpp
 * @version 1.0
 */
public class HasNoParametersException extends InputException {

    /**
     * Instantiates a new HasNoParameters exception.
     *
     * @param instruction the instruction
     * @param params      the parameters for the instruction
     */
    public HasNoParametersException(String instruction, String params) {
        super(String.format(ExceptionMessage.COMMAND_HAS_NO_PARAMETERS.toString(), instruction, params));
    }
}
