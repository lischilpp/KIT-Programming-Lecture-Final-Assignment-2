package edu.kit.informatik.ui.exceptions;

import edu.kit.informatik.ui.strings.ExceptionMessage;

/**
 * An exception thrown when the parameters of a command entered by the user are invalid.
 *
 * @author Linus Schilpp
 * @version 1.0
 */
public class InvalidParametersException extends InputException {
    /**
     * Instantiates a new InvalidParameterException.
     *
     * @param usageSyntax The syntax of the parameters
     */
    public InvalidParametersException(String usageSyntax) {
        super(String.format(ExceptionMessage.INVALID_PARAMETERS.toString(), usageSyntax));
    }
}
