package hr.fer.zemris.java.hw07.shell.commands;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.EnvironmentImpl;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import hr.fer.zemris.java.hw07.shell.Util;

import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.*;


/**
 * This command changes to the directory stored by the PUSHD command.
 *
 * @author matej
 */
public class PopdCommand implements ShellCommand {
    /**
     * Name of the command
     */
    private static final String NAME = Commands.POPD.getName();
    /**
     * Description of the command
     */
    private static final List<String> DESC;

    static {
        DESC = new ArrayList<>();
        String[] descArr = {
                "Changes to the directory stored by the PUSHD command.",
                "",
                "POPD",
        };
        DESC.addAll(Arrays.asList(descArr));
    }

    /**
     * {@inheritDoc} This command changes to the directory stored by the PUSHD command.
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
            env.writeln("Stack is empty - current directory is unchanged.");
            return ShellStatus.CONTINUE;
        }

        Path current = stack.pop();
        if (Files.notExists(current)) {
            env.writeln("Path no longer exists - removed from stack.");
        }

        env.setCurrentDirectory(current);
        env.writeln("Changed current directory to " + env.getCurrentDirectory().toString());
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
