package edu.kit.informatik.ui;

import edu.kit.informatik.Terminal;
import edu.kit.informatik.logic.Assembly;
import edu.kit.informatik.logic.exceptions.LogicException;

import java.util.*;

/**
 * A list containing all commands that are available to a command line interface.
 *
 * @author Linus Schilpp
 * @version 1.0
 */
class CommandList {
    /**
     * Contains all commands that can be called from a CommandLineInterface.
     *
     * Using a HashMaps of anonymous subclasses instead of the proposed command enum,
     * since HashMaps are faster and subclasses are better in terms of polymorphism.
     */
    private static final Map<String, Command> COMMANDS = new HashMap<String, Command>() { {
        put("addAssembly",
            new Command(
            "addAssembly <nameAssembly>=<amount1>:<name1>;<amount2>:<name2>;...;<amountn>:<namen>") {

            @Override
            public void execute(CommandLineInterface cli) {
                try {
                    Assembly assembly = cli.createAssemblyFromString(params);
                    cli.workbench.addBOM(assembly);
                    cli.printSuccessMessage();
                } catch (LogicException e) {
                    Terminal.printError(e.getMessage());
                }
            }
            @Override
            public boolean isValid() {
                return params.matches(Command.ASSEMBLY_REGEX);
            }
        });
        put("removeAssembly",
            new Command("removeAssembly <nameAssembly>") {

            @Override
            public void execute(CommandLineInterface cli) {
                try {
                    cli.workbench.removeBOM(params);
                    cli.printSuccessMessage();
                } catch (LogicException e) {
                    Terminal.printError(e.getMessage());
                }
            }
            @Override
            public boolean isValid() {
                return params.matches(Command.ALPHABET_REGEX);
            }
        });
        put("printAssembly",
            new Command("printAssembly <nameAssembly>") {

            @Override
            public void execute(CommandLineInterface cli) {
                try {
                    cli.printAssemblyByName(params);
                } catch (LogicException e) {
                    Terminal.printError(e.getMessage());
                }
            }
            @Override
            public boolean isValid() {
                return params.matches(Command.ALPHABET_REGEX);
            }
        });
        put("getAssemblies",
            new Command("getAssemblies <nameAssembly>") {

            @Override
            public void execute(CommandLineInterface cli) {
                try {
                    cli.printBOMCountsOf(params);
                } catch (LogicException e) {
                    Terminal.printError(e.getMessage());
                }
            }
            @Override
            public boolean isValid() {
                return params.matches(Command.ALPHABET_REGEX);
            }
        });
        put("getComponents",
            new Command("getComponents <nameAssembly>") {

            @Override
            public void execute(CommandLineInterface cli) {
                try {
                    cli.printComponentCountsOf(params);
                } catch (LogicException e) {
                    Terminal.printError(e.getMessage());
                }
            }
            @Override
            public boolean isValid() {
                return params.matches(Command.ALPHABET_REGEX);
            }
        });
        put("addPart",
            new Command("addPart <nameAssembly>+<amount>:<name>") {

            @Override
            @SuppressWarnings("Duplicates")
            public void execute(CommandLineInterface cli) {
                Map<String, String> paramMap = getParameterMap();

                String assemblyName = paramMap.get("nameAssembly");
                String partName = paramMap.get("name");
                String amountStr = paramMap.get("amount");

                try {
                    long partAmount = cli.getPartAmountFromString(assemblyName, partName, amountStr);
                    cli.workbench.addPartAmountToBOM(assemblyName, partName, partAmount);
                    cli.printSuccessMessage();
                } catch (LogicException e) {
                    Terminal.printError(e.getMessage());
                }
            }
            @Override
            public boolean isValid() {
                return params.matches(Command.ADD_PART_REGEX);
            }
        });
        put("removePart",
            new Command("removePart <nameAssembly>-<amount>:<name>") {

            @Override
            @SuppressWarnings("Duplicates")
            public void execute(CommandLineInterface cli) {
                Map<String, String> paramMap = getParameterMap();

                String assemblyName = paramMap.get("nameAssembly");
                String partName = paramMap.get("name");
                String amountStr = paramMap.get("amount");

                try {
                    long partAmount = cli.getPartAmountFromString(assemblyName, partName, amountStr);
                    cli.workbench.removePartAmountFromBOM(assemblyName, partName, partAmount);
                    cli.printSuccessMessage();
                } catch (LogicException e) {
                    Terminal.printError(e.getMessage());
                }
            }
            @Override
            public boolean isValid() {
                return params.matches(Command.REMOVE_PART_REGEX);
            }
        });
        put("quit", new Command("quit") {
            @Override
            public void execute(CommandLineInterface cli) {
                cli.close();
            }
            @Override
            public boolean isValid() {
                return params.length() == 0;
            }
        });
    } };

    /**
     * Checks if an instruction is contained in the list of commands.
     *
     * @param instruction the instruction string
     * @return whether the instruction is contained in the list of commands
     */
    static boolean containsInstruction(String instruction) {
        return COMMANDS.containsKey(instruction);
    }

    /**
     * Gets the command for an instruction.
     *
     * @param instruction the instruction string
     * @return the command for the instruction string
     */
    static Command get(String instruction) {
        return COMMANDS.get(instruction);
    }
}
