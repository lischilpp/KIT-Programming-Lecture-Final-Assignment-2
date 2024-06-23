package edu.kit.informatik.ui.strings;

/**
 * Contains all predefined messages for different exceptions.
 *
 * @author Linus Schilpp
 * @version 1.0
 */
public enum ExceptionMessage {

    /**
     * The error message for an InvalidInstructionException.
     */
    INVALID_INSTRUCTION("%s is not a valid instruction"),

    /**
     * The error message for an InvalidParametersException.
     */
    INVALID_PARAMETERS("invalid parameters. Usage: %s"),

    /**
     * The error message for an HasNoParametersException.
     */
    COMMAND_HAS_NO_PARAMETERS("incorrect input format, the %s command does not accept any parameters: %s"),

    /**
     * The error message for an AssemblyCreatesCycleException.
     */
    ASSEMBLY_CREATES_CYCLE("the specified BOM %s would create a cycle in the product structure: %s"),

    /**
     * The error message for an AssemblyExistsException.
     */
    ASSEMBLY_EXISTS("a BOM named %s already exists in the system"),

    /**
     * The error message for an AssemblyNotExistingException.
     */
    ASSEMBLY_NOT_EXISTING("no BOM exists in the system for the specified name: %s"),

    /**
     * The error message for an DuplicatePartsException.
     */
    DUPLICATE_PARTS("the names of the parts in the specified BOM Y appear twice: Z"),

    /**
     * The error message for an InvalidPartAmountException.
     */
    INVALID_PART_AMOUNT("the amount for the component %s in the BOM %s is too high: %d"),

    /**
     * The error message for an InvalidPartAmountException with partAmount passed as string.
     */
    INVALID_PART_AMOUNT_STRING("the amount for the component %s in the BOM %s is too high: %s"),

    /**
     * The error message for an PartAmountNotExistingException.
     */
    PART_AMOUNT_NOT_EXISTING("the BOM %s does not contain the part A in the specified amount: %s"),

    /**
     * The error message for an PartHasCycleException.
     */
    PART_HAS_CYCLE("adding the part %s to the BOM %s would create a cycle in the structure: %s"),

    /**
     * The error message for an PartNotExistingException.
     */
    PART_NOT_EXISTING("the BOM %s does not contain the specified part: %s");


    private final String message;

    /**
     * Defines a new ExceptionMessage
     *
     * @param message the message string
     */
    ExceptionMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return this.message;
    }
}
