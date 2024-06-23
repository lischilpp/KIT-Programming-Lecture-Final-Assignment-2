package edu.kit.informatik.logic.exceptions;

import edu.kit.informatik.ui.strings.ExceptionMessage;

/**
 * An exception thrown when a part of an assembly does not exist.
 *
 * @author Linus Schilpp
 * @version 1.0
 */
public class PartNotExistingException extends LogicException {

    /**
     * Instantiates a new PartNotExistingException.
     *
     * @param assemblyName the name of the assembly
     * @param partName     the name of the part
     */
    public PartNotExistingException(String assemblyName, String partName) {
        super(String.format(ExceptionMessage.PART_NOT_EXISTING.toString(), assemblyName, partName));
    }

}
