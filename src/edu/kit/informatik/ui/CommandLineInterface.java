package edu.kit.informatik.ui;


import edu.kit.informatik.Terminal;
import edu.kit.informatik.logic.Assembly;
import edu.kit.informatik.logic.AssemblyType;
import edu.kit.informatik.logic.Workbench;
import edu.kit.informatik.logic.exceptions.DuplicatePartsException;
import edu.kit.informatik.logic.exceptions.InvalidPartAmountException;
import edu.kit.informatik.logic.exceptions.LogicException;
import edu.kit.informatik.ui.exceptions.InputException;

import java.util.*;
import java.util.Map.Entry;

/**
 * Provides an interactive shell to interact with the user.
 * The user can enter commands which will be parsed and
 * executed if they are valid
 * if they are invalid an error message will be printed
 *
 * @author Linus Schilpp
 * @version 1.0
 */
public class CommandLineInterface {

    /**
     * Separates the name of an assembly from its structure.
     */
    static final String ASSEMBLY_NAME_SEPARATOR = "=";
    /**
     * Separates several parts of an assembly.
     */
    static final String ASSEMBLY_PART_SEPARATOR = ";";
    /**
     * Separates the name of an part from its amount.
     */
    static final String PART_AMOUNT_SEPARATOR = ":";

    private static final String MESSAGE_SUCCESS = "OK";
    private static final String IS_COMPONENT_STRING = "COMPONENT";
    private static final String IS_EMPTY_STRING = "EMPTY";

    /**
     * Comparator to sort the entries HashMaps by their value in descending order.
     */
    private static final Comparator<Entry<String, Long>> COMPARATOR_VALUE_DESC
            = Comparator.comparing(Map.Entry::getValue, Comparator.reverseOrder());
    /**
     * Comparator to sort the entries of HashMaps by their key in descending order.
     */
    private static final Comparator<Entry<String, Long>> COMPARATOR_KEY_ASC
            = Comparator.comparing(Map.Entry::getKey);

    /**
     * The workbench associated with the CommandLineInterface.
     */
    final Workbench workbench;

    private boolean quitRequested;

    /**
     * Instantiates a new CommandLineInterface.
     */
    public CommandLineInterface() {
        this.quitRequested = false;
        this.workbench = new Workbench();
    }

    /**
     * Starts the CommandLineInterface awaiting user input.
     */
    public void start() {
        while (this.isRunning()) {
            String commandStr = Terminal.readLine();

            try {
                Command cmd = Command.fromString(commandStr);
                cmd.execute(this);
            } catch (InputException e) {
                printError(e.getMessage());
            }
        }
    }

    /**
     * Returns whether the CommandLineInterface is running.
     *
     * @return whether it is running
     */
    boolean isRunning() {
        return !this.quitRequested;
    }

    /**
     * Closes the CommandLineInterface.
     */
    void close() {
        this.quitRequested = true;
    }

    /**
     * Prints a line to console.
     *
     * @param obj the obj
     */
    void printLine(Object obj) {
        Terminal.printLine(obj.toString());
    }

    /**
     * Prints an error to console.
     *
     * @param msg the msg
     */
    void printError(String msg) {
        Terminal.printError(msg);
    }

    /**
     * Prints the success message to console.
     */
    void printSuccessMessage() {
        printLine(MESSAGE_SUCCESS);
    }


    /**
     * Creates an assembly from its string representation.
     *
     * @param assemblyStr the string representation of the assembly
     * @return the assembly
     * @throws DuplicatePartsException    when two parts declared in the string have the same name
     * @throws InvalidPartAmountException when the amount of a part declared in the string is invalid
     */
    Assembly createAssemblyFromString(String assemblyStr) throws DuplicatePartsException, InvalidPartAmountException {
        String[] assemblyStringSplit = assemblyStr.split(ASSEMBLY_NAME_SEPARATOR);

        String assemblyName = assemblyStringSplit[0];
        String[] partStrings = assemblyStringSplit[1].split(ASSEMBLY_PART_SEPARATOR);

        HashMap<String, Long> partEntries = new HashMap<>();
        Set<String> duplicatePartNames = new HashSet<>();

        for (String partString : partStrings) {
            String[] partStringSplit = partString.split(PART_AMOUNT_SEPARATOR);
            String partName = partStringSplit[1];
            Long partAmount = getPartAmountFromString(assemblyName, partName, partStringSplit[0]);

            if (partEntries.containsKey(partName)) {
                duplicatePartNames.add(partName);
            } else {
                partEntries.put(partName, partAmount);
            }
        }

        if (duplicatePartNames.size() != 0) {
            throw new DuplicatePartsException(assemblyName, duplicatePartNames);
        }

        return new Assembly(assemblyName, partEntries);
    }

    /**
     * Prints an assembly to the console.
     *
     * @param assemblyName the name of the assembly
     * @throws LogicException when the assembly does not exist
     */
    void printAssemblyByName(String assemblyName) throws LogicException {
        if (workbench.hasComponent(assemblyName)) {
            Terminal.printLine(IS_COMPONENT_STRING);
        } else {
            Assembly assembly = workbench.getBOM(assemblyName);
            // partCounts means in this case only the direct parts not the parts of sub-parts
            List<Entry<String, Long>> partCounts = new ArrayList<>(assembly.getPartEntries());

            partCounts.sort(COMPARATOR_KEY_ASC);
            printPartCounts(partCounts);
        }
    }

    /**
     * Prints the counts of all BOMs the assembly consists of including the assemblies of sub-parts.
     *
     * @param assemblyName the name of the assembly
     * @throws LogicException when the assembly does not exist
     */
    void printBOMCountsOf(String assemblyName) throws LogicException {
        Assembly assembly = workbench.getBOM(assemblyName);
        HashMap<String, Long> bomCounts = workbench.getPartCountsOf(assembly, AssemblyType.BOM);

        if (bomCounts.size() == 0) {
            Terminal.printLine(IS_EMPTY_STRING);
        } else {
            List<Entry<String, Long>> bomCountList = new ArrayList<>(bomCounts.entrySet());

            bomCountList.sort(COMPARATOR_VALUE_DESC.thenComparing(COMPARATOR_KEY_ASC));
            printPartCounts(bomCountList);
        }
    }

    /**
     * Prints counts of all components the assembly consists of including the components of sub-parts.
     *
     * @param assemblyName the name of the assembly
     * @throws LogicException when the assembly does not exist
     */
    void printComponentCountsOf(String assemblyName) throws LogicException {
        Assembly assembly = workbench.getBOM(assemblyName);
        HashMap<String, Long> componentCounts = workbench.getPartCountsOf(assembly, AssemblyType.COMPONENT);
        List<Entry<String, Long>> componentCountList = new ArrayList<>(componentCounts.entrySet());

        componentCountList.sort(COMPARATOR_VALUE_DESC.thenComparing(COMPARATOR_KEY_ASC));
        printPartCounts(componentCountList);
    }

    /**
     * Prints key value pairs of partNames and their respective amount to console.
     *
     * @param partEntries a list of all entries
     */
    private void printPartCounts(Iterable<Entry<String, Long>> partEntries) {
        StringBuilder sb = new StringBuilder();

        for (Entry<String, Long> partEntry : partEntries) {
            String partName = partEntry.getKey();
            long partAmount = partEntry.getValue();

            if (sb.length() > 0) {
                sb.append(ASSEMBLY_PART_SEPARATOR);
            }
            sb.append(partName).append(PART_AMOUNT_SEPARATOR).append(partAmount);
        }
        Terminal.printLine(sb.toString());
    }

    /**
     * @param bomName The name of the bom that has the part
     * @param partName the name of the part
     * @param partAmountStr the string representation of the amount of the part
     * @return the amount represented by the string
     * @throws InvalidPartAmountException when the amount is not a valid number
     */
    public long getPartAmountFromString(String bomName, String partName, String partAmountStr)
            throws InvalidPartAmountException {

        try {
            long partAmount = Long.parseLong(partAmountStr);
            return partAmount;
        } catch (NumberFormatException e) {
            throw new InvalidPartAmountException(bomName, partName, partAmountStr);
        }
    }
}
