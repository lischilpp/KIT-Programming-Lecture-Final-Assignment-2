package edu.kit.informatik.ui;

import edu.kit.informatik.ui.exceptions.HasNoParametersException;
import edu.kit.informatik.ui.exceptions.InputException;
import edu.kit.informatik.ui.exceptions.InvalidInstructionException;
import edu.kit.informatik.ui.exceptions.InvalidParametersException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Describes the structure of a command that can be called from a CommandLineInterface
 * and provides a static method to get an specific command from its String representation
 *
 * @author Linus Schilpp
 * @version 1.0
 */
abstract class Command {
    /**
     * Matches an alphabetical non-empty string.
     */
    static final String ALPHABET_REGEX = "[a-zA-Z]+";
    /**
     * Matches an amount in general: a natural number.
     */
    static final String AMOUNT_REGEX = "[1-9][0-9]*";
    /**
     * Matches the string representation of an assembly part entry.
     */
    static final String PART_ENTRY_REGEX
            = AMOUNT_REGEX + CommandLineInterface.PART_AMOUNT_SEPARATOR + ALPHABET_REGEX;

    /**
     * Matches the string representation of several assembly part entries.
     */
    static final String PART_ENTRIES_REGEX
            = PART_ENTRY_REGEX + "(" + CommandLineInterface.ASSEMBLY_PART_SEPARATOR + PART_ENTRY_REGEX + ")*";

    /**
     * Matches the string representation of an assembly.
     */
    static final String ASSEMBLY_REGEX
            = ALPHABET_REGEX + CommandLineInterface.ASSEMBLY_NAME_SEPARATOR + PART_ENTRIES_REGEX;

    /**
     * Matches the syntax of the command to add a part to an assembly.
     */
    static final String ADD_PART_REGEX = ALPHABET_REGEX + "\\+" + PART_ENTRY_REGEX;
    /**
     * Matches the syntax of the command to remove a part from an assembly.
     */
    static final String REMOVE_PART_REGEX = ALPHABET_REGEX + "-" + PART_ENTRY_REGEX;


    private static final String INSTRUCTION_SEPARATOR = " ";
    private static final String USAGE_SYNTAX_PARAMETER_REGEX = "<(.+?)>";
    private static final String ALPHANUMERIC_REGEX = "[a-zA-Z0-9]+";

    /**
     * The parameters for the instruction.
     * Can be empty if no parameters passed
     */
    String params;

    private final String usageSyntax;

    /**
     * Instantiates a new Command.
     * Can be only called from a subclass that implements a "execute" and "isValid" method
     *
     * @param usageSyntax The syntax of the parameters
     */
    Command(String usageSyntax) {
        this.usageSyntax = usageSyntax;
    }

    /**
     * Creates an Command from its String representation.
     *
     * @param commandStr the String representation of a command
     * @return the actual executable command
     * @throws InputException throws when the string cannot be parsed into a command
     */
    static Command fromString(final String commandStr) throws InputException {
        String[] commandSplit = commandStr.split(INSTRUCTION_SEPARATOR, 2);
        String instruction = commandSplit[0];
        String params = "";
        boolean hasParameters = commandSplit.length > 1;

        if (hasParameters) {
            params = commandSplit[1];
        }

        if (CommandList.containsInstruction(instruction)) {
            Command cmd = CommandList.get(instruction);
            cmd.params = params;

            if (cmd.usageSyntax.equals(instruction) && params.length() > 0) {
                throw new HasNoParametersException(instruction, params);
            } else if (!cmd.isValid())
                throw new InvalidParametersException(cmd.usageSyntax);

            return cmd;
        } else
            throw new InvalidInstructionException(instruction);
    }

    /**
     * Executes the command.
     *
     * @param cli the CommandLineInterface in whose context the command is going to be executed
     */
    public abstract void execute(CommandLineInterface cli);

    /**
     * Determines whether all provided parameters of a command are valid.
     *
     * @return true if all parameters have the correct syntax
     */
    protected abstract boolean isValid();

    /**
     * Gets a map of all parameter.
     * The key equals the parameters usage syntax name.
     *
     * @return a map of all parameters
     */
    Map<String, String> getParameterMap() {
        Map<String, String> parameterMap = new HashMap<>();
        List<String> paramNames = getValuesOfMatches(USAGE_SYNTAX_PARAMETER_REGEX, usageSyntax, 1);
        List<String> paramValues = getValuesOfMatches(ALPHANUMERIC_REGEX, params, 0);

        assert paramNames.size() == paramValues.size();

        for (int i = 0; i < paramNames.size(); i++) {
            parameterMap.put(paramNames.get(i), paramValues.get(i));
        }

        return parameterMap;
    }

    /**
     * Gets the value of all matches of the regex in the string
     * @param regex the match regex
     * @param str the string to search for matches
     * @param wrapperSize the amount of letters to remove from the beginning and end of the value
     * @return a list of all matches values
     */
    private List<String> getValuesOfMatches(String regex, String str, int wrapperSize) {
        Matcher matcher = Pattern.compile(regex).matcher(str);
        List<String> matches = new ArrayList<>();
        while (matcher.find()) {
            matches.add(matcher.group(wrapperSize));
        }
        return matches;
    }
}