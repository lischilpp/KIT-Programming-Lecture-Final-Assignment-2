package edu.kit.informatik.logic.exceptions;

import edu.kit.informatik.ui.strings.ExceptionMessage;

/**
 * An exception thrown when the amount of a specified assembly part is invalid.
 *
 * @author Linus Schilpp
 * @version 1.0
 */
public class InvalidPartAmountException extends LogicException {

    /**
     * Instantiates a new InvalidPartAmountException.
     *
     * @param bomName    the name of the BOM
     * @param partName   the name the part
     * @param partAmount the amount of the part
     */
    public InvalidPartAmountException(String bomName, String partName, long partAmount) {
        super(String.format(ExceptionMessage.INVALID_PART_AMOUNT.toString(), partName, bomName, partAmount));
    }

    /**
     * Instantiates a new InvalidPartAmountException.
     *
     * @param bomName    the name of the BOM
     * @param partName   the name the part
     * @param partAmountStr the amount of the part as string
     */
    public InvalidPartAmountException(String bomName, String partName, String partAmountStr) {
        super(String.format(ExceptionMessage.INVALID_PART_AMOUNT_STRING.toString(), partName, bomName, partAmountStr));
    }

}
