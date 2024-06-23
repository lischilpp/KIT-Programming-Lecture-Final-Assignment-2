package edu.kit.informatik.logic.exceptions;

import edu.kit.informatik.ui.strings.ExceptionMessage;

import java.util.List;

/**
 * An exception thrown when a part creates a cycle in the product structure.
 *
 * @author Linus Schilpp
 * @version 1.0
 */
public class PartHasCycleException extends LogicException {

    /**
     * Instantiates a new PartHasCycleException.
     *
     * @param bomName  the name of the BOM
     * @param partName the name of the part
     * @param trace    the trace of the cycle created by adding the part to the BOM
     */
    public PartHasCycleException(String bomName, String partName, List<String> trace) {
        super(String.format(ExceptionMessage.PART_HAS_CYCLE.toString(),
                            partName, bomName, String.join("-", trace)));
    }

}
