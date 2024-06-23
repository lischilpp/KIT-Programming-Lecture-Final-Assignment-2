package edu.kit.informatik.logic.exceptions;

import edu.kit.informatik.logic.Assembly;
import edu.kit.informatik.ui.strings.ExceptionMessage;

import java.util.List;

/**
 * An exception thrown when an assembly creates a cycle in the product structure.
 *
 * @author Linus Schilpp
 * @version 1.0
 */
public class AssemblyCreatesCycleException extends LogicException {

    /**
     * Instantiates a new AssemblyCreatesCycleException.
     *
     * @param assembly the assembly
     * @param trace    the trace of the cycle created by the assembly
     */
    public AssemblyCreatesCycleException(Assembly assembly, List<String> trace) {
        super(String.format(ExceptionMessage.ASSEMBLY_CREATES_CYCLE.toString(),
                            assembly.getName(), String.join("-", trace)));
    }

}
