package edu.kit.informatik.logic.exceptions;

import edu.kit.informatik.ui.strings.ExceptionMessage;

/**
 * An exception thrown when an assembly already exists.
 *
 * @author Linus Schilpp
 * @version 1.0
 */
public class AssemblyExistsException extends LogicException {

    /**
     * Instantiates a new AssemblyExistsException.
     *
     * @param assemblyName the name of the assembly
     */
    public AssemblyExistsException(String assemblyName) {
        super(String.format(ExceptionMessage.ASSEMBLY_EXISTS.toString(), assemblyName));
    }

}
