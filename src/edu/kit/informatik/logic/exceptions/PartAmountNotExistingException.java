package edu.kit.informatik.logic.exceptions;

import edu.kit.informatik.ui.strings.ExceptionMessage;

/**
 * An exception thrown when an assembly does not have a part for the given amount.
 *
 * @author Linus Schilpp
 * @version 1.0
 */
public class PartAmountNotExistingException extends LogicException {

    /**
     * Instantiates a new PartAmountNotExistingException.
     *
     * @param partName the name of the part
     * @param amount   the amount of the part
     */
    public PartAmountNotExistingException(String partName, long amount) {
        super(String.format(ExceptionMessage.PART_AMOUNT_NOT_EXISTING.toString(), partName, amount));
    }

}
