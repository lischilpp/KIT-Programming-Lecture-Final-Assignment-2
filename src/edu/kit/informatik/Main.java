package edu.kit.informatik;


import edu.kit.informatik.ui.CommandLineInterface;

/**
 * The Main class
 * @author Linus Schilpp
 * @version 1.0
 */
public class Main {
    /**
     * The entry point of application.
     *
     * @param args the commandLine arguments passed to the application
     */
    public static void main(String[] args) {
        CommandLineInterface cli = new CommandLineInterface();
        cli.start();
    }
}

