package hr.fer.zemris.java.hw07.shell.commands;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.EnvironmentImpl;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import hr.fer.zemris.java.hw07.shell.Util;

import java.nio.file.Path;
import java.util.*;


/**
 * This command removes the last directory stored by the PUSHD command.
 *
 * @author matej
 */
public class MassrenameCommand implements ShellCommand {
    /**
     * Name of the command
     */
    private static final String NAME = Commands.MASSRENAME.getName();
    /**
     * Description of the command
     */
    private static final List<String> DESC;

    static {
        DESC = new ArrayList<>();
        String[] descArr = {
                "Removes the last directory stored by the PUSHD command.",
                "",
                "DROPD",
        };
        DESC.addAll(Arrays.asList(descArr));
    }

    /**
     * {@inheritDoc} This command removes the last directory stored by the PUSHD command.
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

        Stack<Path> stack = (Stack<Path>) env.getSharedData(EnvironmentImpl.STACK_NAME);
        if (stack == null || stack.size() == 0) {
            env.writeln("No directory stored.");
            return ShellStatus.CONTINUE;
        }

        env.writeln("Removed directory " + stack.pop().toString());
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
