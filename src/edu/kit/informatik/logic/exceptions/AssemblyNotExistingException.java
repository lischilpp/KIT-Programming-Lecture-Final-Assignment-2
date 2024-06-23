package edu.kit.informatik.logic.exceptions;

import edu.kit.informatik.ui.strings.ExceptionMessage;

/**
 * An exception thrown when an assembly does not exist.
 *
 * @author Linus Schilpp
 * @version 1.0
 */
public class AssemblyNotExistingException extends LogicException {

    /**
     * Instantiates a new AssemblyNotExistingException.
     *
     * @param assemblyName the name of the assembly
     */
    public AssemblyNotExistingException(String assemblyName) {
        super(String.format(ExceptionMessage.ASSEMBLY_NOT_EXISTING.toString(), assemblyName));
    }

}
