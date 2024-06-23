package edu.kit.informatik.ui;

import edu.kit.informatik.Terminal;

/**
 * The type Input emulator.
 *
 * @author Linus Schilpp
 * @version 1.0
 */
class InputEmulator {

    private static final String[][] TESTS = {
        // removed as those tests were provided by the assignment and not authored by me
    };

    private static final int TEST_NUMBER = 7;
    private static int inputIndex = 0;
    private static final String[] TEST = TESTS[TEST_NUMBER];

    /**
     * Read line string.
     *
     * @return the string
     */
    public static String readLine() {
        String line;
        if (inputIndex < TEST.length) {
            line = TEST[inputIndex];
            inputIndex++;
        } else {
            line = "quit";
        }
        Terminal.printLine("> " + line);
        return line;
    }
}
