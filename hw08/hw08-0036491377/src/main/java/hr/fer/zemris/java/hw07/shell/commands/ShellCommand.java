package hr.fer.zemris.java.hw07.shell.commands;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

import java.util.List;

/**
 * Interface for a generic shell command. Provides method to execute the command and methods to get information about
 * the command.
 *
 * @author matej
 */
public interface ShellCommand {
    /**
     * Executes the specific command.
     *
     * @param env environment in which the command is executed
     * @param arguments arguments passed to the command through the shell
     *
     * @return TERMINATE if an IOException occurs while reading or writing to the user, CONTINUE otherwise
     */
    ShellStatus executeCommand(Environment env, String arguments);

    /**
     * Returns the name of the command.
     *
     * @return name of the command
     */
    String getCommandName();

    /**
     * Returns a description of the command as a list of strings.
     *
     * @return list of strings with command descriptions
     */
    List<String> getCommandDescription();
}
