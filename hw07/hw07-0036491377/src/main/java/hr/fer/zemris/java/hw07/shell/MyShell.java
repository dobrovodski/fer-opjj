package hr.fer.zemris.java.hw07.shell;

import hr.fer.zemris.java.hw07.shell.commands.ShellCommand;

import java.util.SortedMap;

/**
 * This is the entry point to the shell. Waits for the user to enter a command, executes it and waits for more input.
 * The existent commands can be seen using the command "help" and "help [command]".
 *
 * @author matej
 */
public class MyShell {
    /**
     * Entry point.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        Environment env = new EnvironmentImpl();
        SortedMap<String, ShellCommand> commands = env.commands();

        try {
            env.writeln("Welcome to MyShell v 1.0");
        } catch (ShellIOException e) {
            System.err.println("Shell couldn't write to output.");
            return;
        }

        while (true) {
            String in = env.readLine();

            // Everything after the first whitespace is considered as the arguments
            String[] split = in.split("\\s+", 2);
            String command = split[0].toLowerCase();
            String arguments;

            if (split.length == 2) {
                arguments = split[1];
            } else {
                arguments = "";
            }

            if (commands.keySet().contains(command)) {
                ShellStatus status = commands.get(command).executeCommand(env, arguments.trim());
                if (status == ShellStatus.TERMINATE) {
                    break;
                }
            } else {
                env.writeln("Not a valid command: " + command);
            }
        }
    }
}
