package hr.fer.zemris.java.hw07.shell.commands;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import hr.fer.zemris.java.hw07.shell.Util;

import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


/**
 * This command changes the current directory to the provided one.
 *
 * @author matej
 */
public class ListdCommand implements ShellCommand {
    /**
     * Name of the command
     */
    private static final String NAME = "cd";
    /**
     * Description of the command
     */
    private static final List<String> DESC;

    static {
        DESC = new ArrayList<>();
        String[] descArr = {
                "Changes the current directory.",
                "",
                "CD [path]",
                "",
                "   path - path to change the current directory to",
        };
        DESC.addAll(Arrays.asList(descArr));
    }

    /**
     * {@inheritDoc} This command prints the current working directory.
     */
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

        try {
            path = env.getCurrentDirectory().resolve(path).normalize();
            env.setCurrentDirectory(path);
            env.writeln("Changed current directory to " + env.getCurrentDirectory().toString());
        } catch (IllegalArgumentException ex) {
            env.writeln(ex.getMessage());
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
