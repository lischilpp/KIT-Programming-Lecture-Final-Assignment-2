package edu.kit.informatik.logic;

import edu.kit.informatik.logic.exceptions.*;

import java.util.*;

/**
 * The core logic of the program.
 *
 * Similar to an actual workbench it allows constructing objects out of different parts and other objects
 * as well as altering their structure.
 *
 * @author Linus Schilpp
 * @version 1.0
 */
public class Workbench {
    private final HashMap<String, Assembly> boms;
    private final Set<String> components;

    /**
     * Instantiates a new Workbench.
     */
    public Workbench() {
        boms = new HashMap<>();
        components = new HashSet<>();
    }

    /**
     * Determines whether a BOM with the given name exists.
     *
     * @param bomName the name of the BOM
     * @return whether it exists
     */
    public boolean hasBOM(String bomName) {
        return boms.containsKey(bomName);
    }

    /**
     * Determines whether a component with the given name exists.
     *
     * @param componentName the name of the component
     * @return whether it exists
     */
    public boolean hasComponent(String componentName) {
        return components.contains(componentName);
    }

    /**
     * Gets the BOM with the given name.
     *
     * @param bomName the name of the BOM
     * @return the BOM
     * @throws LogicException when no BOM with the given name exists
     */
    public Assembly getBOM(String bomName) throws LogicException {
        if (!hasBOM(bomName)) {
            throw new AssemblyNotExistingException(bomName);
        }
        return boms.get(bomName);
    }

    /**
     * Adds a BOM to the workbench.
     *
     * @param bom the bom to add
     * @throws LogicException when a bom with the given name already exists
     *                        or if the bom would create a cycle in the product structure
     */
    public void addBOM(Assembly bom) throws LogicException {
        if (hasBOM(bom.getName())) {
            throw new AssemblyExistsException(bom.getName());
        }

        List<String> cycle = getFirstCycleOfBOM(bom);
        if (cycle != null) {
            throw new AssemblyCreatesCycleException(bom, cycle);
        }

        boms.put(bom.getName(), bom);
        updateComponents();
    }

    /**
     * Removes the bom with the given name from the workbench.
     *
     * @param bomName the name of the bom
     * @throws LogicException when no bom for the given name exists
     */
    public void removeBOM(String bomName) throws LogicException {
        if (!hasBOM(bomName)) {
            throw new AssemblyNotExistingException(bomName);
        }

        boms.remove(bomName);
        updateComponents();
    }

    /**
     * Adds the given amount of a part to a BOM.
     *
     * @param bomName  the name of the BOM
     * @param partName the name of the part to add
     * @param amount   the amount of the part
     * @throws LogicException when no bom with the given name exists
     *                        or adding the part would create a cycle in the product structure
     *                        or adding the amount to the BOM fails
     */
    public void addPartAmountToBOM(String bomName, String partName, Long amount) throws LogicException {
        Assembly bom = getBOM(bomName);

        List<String> trace = getFirstCycleOfNewPart(bom, partName);
        if (trace != null) {
            throw new PartHasCycleException(bomName, partName, trace);
        }

        bom.addPartAmount(partName, amount);
        updateComponents();
    }

    /**
     * Removes the given amount of a part from a BOM.
     *
     * @param bomName  the name of the BOM
     * @param partName the name of the part to remove
     * @param amount   the amount of the part
     * @throws LogicException when no bom with the given name exists
     *                        or removing the amount from the BOM fails
     */
    public void removePartAmountFromBOM(String bomName, String partName, Long amount) throws LogicException {
        Assembly assembly = getBOM(bomName);
        assembly.removePartAmount(partName, amount);

        // remove the assembly from the boms when it is empty and therefore a component
        if (assembly.getPartNames().size() == 0) {
            boms.remove(assembly.getName());
        }
        updateComponents();
    }

    /**
     * Gets the counts of all parts with the given assembly type.
     *
     * @param bom the BOM to get counts of
     * @param assemblyType the type of the assembly
     * @return a map of key value pair of part names and their respective amount
     */
    public HashMap<String, Long> getPartCountsOf(Assembly bom, AssemblyType assemblyType) {
        HashMap<String, Long> counts = new HashMap<>();

        for (Map.Entry<String, Long> partEntry : bom.getPartEntries()) {
            String partName = partEntry.getKey();
            Long partAmount = partEntry.getValue();
            boolean partIsBom = hasBOM(partName);

            if (partIsBom) {
                Assembly part = boms.get(partName);
                HashMap<String, Long> partCounts = getPartCountsOf(part, assemblyType);
                // add the counts of the part to the overall amounts
                for (Map.Entry<String, Long> entry : partCounts.entrySet()) {
                    String key = entry.getKey();
                    long newValue = counts.getOrDefault(key, (long) 0) + partAmount * entry.getValue();
                    counts.put(key, newValue);
                }
            }
            if (   (assemblyType == AssemblyType.BOM       &&  partIsBom)
                || (assemblyType == AssemblyType.COMPONENT && !partIsBom)) {

                counts.merge(partName, partAmount, Long::sum);
            }
        }
        return counts;
    }


    /**
     * Gets the first cycle that the given BOM creates in the product structure
     *
     * @param bom the BOM
     * @return the first cycle or null if no circle has been found
     */
    private List<String> getFirstCycleOfBOM(Assembly bom) {
        // try to find a cycle in the parts of the bom
        for (String partName : bom.getPartNames()) {
            List<String> trace = getFirstCycleOfNewPart(bom, partName);
            if (trace != null) {
                return trace;
            }
        }
        return null;
    }

    /**
     * Gets the first cycle that adding a part to a BOM would create in the product structure
     *
     * @param bom the BOM the part gets added to
     * @param partName the name of the part to add
     * @return the first cycle or null if no circle has been found
     */
    private List<String> getFirstCycleOfNewPart(Assembly bom, String partName) {
        List<String> trace = getFirstCycleTrace(partName, new ArrayList<>(Arrays.asList(bom.getName())));
        if (trace != null) {
            // shift the path by one to make it start by the bom that is the root of the cycle
            trace.remove(0);
            trace.add(trace.get(0));
            return trace;
        }
        return null;
    }

    /**
     * Gets the trace of the first cycle that is being found by traversing all assemblies down to their components
     *
     * @param assemblyName the name of the assembly to traverse from
     * @param trace the trace from to first assembly to the current assembly
     * @return the trace of the first cycle represented by a list of assembly names
     */
    private List<String> getFirstCycleTrace(String assemblyName, List<String> trace) {
        if (trace.contains(assemblyName)) {
            trace.add(assemblyName);
            return trace;
        } else if (hasBOM(assemblyName)) {
            trace.add(assemblyName);
            Assembly bom = boms.get(assemblyName);

            for (String partName : bom.getPartNames()) {
                List<String> partTrace = getFirstCycleTrace(partName, new ArrayList<>(trace));
                if (partTrace != null) {
                    return partTrace;
                }
            }
        }
        return null;
    }

    /* this recalculation of components is costly but since BOM (Bill of Material) are rarely altered in production
       the performance gain when determining whether an assembly part is an individual part outweighs this cost
     */
    private void updateComponents() {
        components.clear();
        // find out the new components
        for (Assembly assembly : boms.values()) {
            for (String partName : assembly.getPartNames()) {
                if (!hasBOM(partName)) {
                    components.add(partName);
                }
            }
        }
    }
}
