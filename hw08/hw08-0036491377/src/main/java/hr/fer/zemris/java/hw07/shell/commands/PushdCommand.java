package hr.fer.zemris.java.hw07.shell.commands;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.EnvironmentImpl;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import hr.fer.zemris.java.hw07.shell.Util;

import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;


/**
 * This command stores the current directory for use by the POPD command, then changes to the specified directory.
 *
 * @author matej
 */
public class PushdCommand implements ShellCommand {
    /**
     * Name of the command
     */
    private static final String NAME = Commands.PUSHD.getName();
    /**
     * Description of the command
     */
    private static final List<String> DESC;

    static {
        DESC = new ArrayList<>();
        String[] descArr = {
                "Stores the current directory for use by the POPD command, then changes to the specified directory.",
                "",
                "PUSHD [path]",
                "",
                "   path - specifies the directory to make the current directory.",
        };
        DESC.addAll(Arrays.asList(descArr));
    }

    /**
     * {@inheritDoc} This command stores the current directory for use by the POPD command, then changes to the
     * specified directory.
     */
    @SuppressWarnings("unchecked")
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        List<String> args = Util.split(arguments);

        if (args == null) {
            env.writeln("Space required after ending quote.");
            return ShellStatus.CONTINUE;
        }

        if (args.size() != 1) {
            env.writeln("This command takes one argument - the path to the new current directory.");
            return ShellStatus.CONTINUE;
        }

        Path path;
        try {
            path = env.getCurrentDirectory().resolve(args.get(0));
        } catch (InvalidPathException ex) {
            env.writeln("Invalid path.");
            return ShellStatus.CONTINUE;
        }

        Path previous = env.getCurrentDirectory();
        try {
            path = path.normalize();
            env.setCurrentDirectory(path);
            env.writeln("Changed current directory to " + env.getCurrentDirectory().toString());
        } catch (IllegalArgumentException ex) {
            env.writeln(ex.getMessage());
            return ShellStatus.CONTINUE;
        }

        Stack<Path> stack;
        if (env.getSharedData(EnvironmentImpl.STACK_NAME) == null) {
            stack = new Stack<>();
            env.setSharedData(EnvironmentImpl.STACK_NAME, stack);
        } else {
            stack = (Stack<Path>) env.getSharedData(EnvironmentImpl.STACK_NAME);
        }

        stack.push(previous);
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
