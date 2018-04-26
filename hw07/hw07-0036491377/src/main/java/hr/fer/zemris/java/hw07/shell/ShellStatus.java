package hr.fer.zemris.java.hw07.shell;

/**
 * Exit status of a command after it has been executed.
 *
 * @author matej
 */
public enum ShellStatus {
    /**
     * The command executed (not necessarily successfully) without an IOException from the standard input/output
     */
    CONTINUE,
    /**
     * Determines the end of the program either due to an IOException or the user exiting the shell
     */
    TERMINATE
}
