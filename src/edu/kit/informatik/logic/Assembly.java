package edu.kit.informatik.logic;

import edu.kit.informatik.logic.exceptions.*;

import java.util.*;


/**
 * An assembly is an object which consists of different parts and their respective amount.
 *
 * @author Linus Schilpp
 * @version 1.0
 */
public class Assembly {
    private static final long PART_AMOUNT_MAX = 1000;

    private String name;
    private HashMap<String, Long> partEntries;

    /**
     * Instantiates a new Assembly.
     *
     * @param name        the name of the assembly
     * @param partEntries the part entries of the assembly
     * @throws InvalidPartAmountException when the amount of a part is invalid
     */
    public Assembly(String name, HashMap<String, Long> partEntries) throws InvalidPartAmountException {
        for (Map.Entry<String, Long> part : partEntries.entrySet()) {
            long amount = part.getValue();

            if (amount > PART_AMOUNT_MAX) {
                throw new InvalidPartAmountException(name, part.getKey(), amount);
            }
        }

        this.name  = name;
        this.partEntries = partEntries;
    }

    /**
     * Gets the name of the assembly.
     *
     * @return the name of the assembly
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the name of all parts and their respective amount as key value pairs.
     *
     * @return a set of part entries
     */
    public Set<Map.Entry<String, Long>> getPartEntries() {
        return partEntries.entrySet();
    }

    /**
     * Gets the names of all parts.
     *
     * @return a list of part names
     */
    Set<String> getPartNames() {
        return partEntries.keySet();
    }

    /**
     * Adds the given amount of a part to the assembly.
     *
     * @param partName the name of the part
     * @param amount   the amount of the part to add
     * @throws InvalidPartAmountException when the amount of the part is invalid
     */
    void addPartAmount(String partName, Long amount) throws InvalidPartAmountException {

        long newAmount = amount + partEntries.getOrDefault(partName, (long) 0);

        if (newAmount > PART_AMOUNT_MAX) {
            throw new InvalidPartAmountException(name, partName, newAmount);
        }
        partEntries.put(partName, newAmount);
    }

    /**
     * Removes the given amount of a part from the assembly.
     *
     * @param partName       the name of the part
     * @param amountToRemove the amount of the part to remove
     * @throws PartNotExistingException       when assembly does not have the specified part
     * @throws PartAmountNotExistingException when the assembly does not have the specified part for the given amount
     */
    void removePartAmount(String partName, Long amountToRemove)
            throws PartNotExistingException, PartAmountNotExistingException {

        if (!partEntries.containsKey(partName)) {
            throw new PartNotExistingException(name, partName);
        }

        long newAmount = partEntries.get(partName) - amountToRemove;

        if (newAmount < 0) {
            throw new PartAmountNotExistingException(partName, amountToRemove);
        } else if (newAmount == 0) {
            partEntries.remove(partName);
        } else {
            partEntries.replace(partName, newAmount);
        }
    }
}
