package edu.kit.informatik.logic.exceptions;


import edu.kit.informatik.ui.strings.ExceptionMessage;

import java.util.Set;

/**
 * An exception thrown when the string representation of an assembly has two parts with the same name.
 *
 * @author Linus Schilpp
 * @version 1.0
 */
public class DuplicatePartsException extends LogicException {

    /**
     * Instantiates a new DuplicatePartsException.
     *
     * @param assemblyName the name of the assembly
     * @param partNames    the names of the parts of the assembly
     */
    public DuplicatePartsException(String assemblyName, Set<String> partNames) {
        super(String.format(ExceptionMessage.DUPLICATE_PARTS.toString(),
                            assemblyName, String.join(",", partNames)));
    }

}
