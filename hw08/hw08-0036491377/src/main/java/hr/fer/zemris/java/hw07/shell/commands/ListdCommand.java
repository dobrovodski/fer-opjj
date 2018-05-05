package hr.fer.zemris.java.hw07.shell.commands;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.EnvironmentImpl;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import hr.fer.zemris.java.hw07.shell.Util;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;


/**
 * This command prints all the directories stored by the PUSHD command.
 *
 * @author matej
 */
public class ListdCommand implements ShellCommand {
    /**
     * Name of the command
     */
    private static final String NAME = Commands.LISTD.getName();
    /**
     * Description of the command
     */
    private static final List<String> DESC;

    static {
        DESC = new ArrayList<>();
        String[] descArr = {
                "Prints all the directories stored by the PUSHD command.",
                "",
                "LISTD",
        };
        DESC.addAll(Arrays.asList(descArr));
    }

    /**
     * {@inheritDoc} This command prints all the directories stored by the PUSHD command.
     */
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        List<String> args = Util.split(arguments);

        if (args == null) {
            env.writeln("Space required after ending quote.");
            return ShellStatus.CONTINUE;
        }

        if (args.size() != 0) {
            env.writeln("This command takes no arguments.");
            return ShellStatus.CONTINUE;
        }

        @SuppressWarnings("unchecked")
        Stack<Path> stack = (Stack<Path>) env.getSharedData(EnvironmentImpl.STACK_NAME);
        if (stack == null || stack.size() == 0) {
            env.writeln("No directory stored.");
            return ShellStatus.CONTINUE;
        }

        for (int i = stack.size() - 1; i >= 0; i--) {
            env.writeln(stack.get(i).toString());
        }

        return ShellStatus.CONTINUE;
    }

    @Override
    public String getCommandName() {
        return NAME;
    }

    @Override
    public List<String> getCommandDescription() {
        return Collections.unmodifiableList(DESC);
    }
}
