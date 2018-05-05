package hr.fer.zemris.java.hw07.shell.commands;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import hr.fer.zemris.java.hw07.shell.Util;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * This command creates the provided directory structure in the current directory.
 *
 * @author matej
 */
public class MkdirCommand implements ShellCommand {
    /**
     * Name of the command
     */
    private static final String NAME = Commands.MKDIR.getName();
    /**
     * Description of the command
     */
    private static final List<String> DESC;

    static {
        DESC = new ArrayList<>();
        String[] descArr = {
                "Creates provided directory structure in current directory.",
                "",
                "MKDIR [structure]",
                "",
                "   structure - directory structure to create."
        };
        DESC.addAll(Arrays.asList(descArr));
    }

    /**
     * {@inheritDoc} This command creates the provided directory structure in the current directory.
     */
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        List<String> args = Util.split(arguments);

        if (args == null) {
            env.writeln("Space required after ending quote.");
            return ShellStatus.CONTINUE;
        }

        if (args.size() != 1) {
            env.writeln("Too many parameters - " + args.get(1));
            return ShellStatus.CONTINUE;
        }

        String dirName = args.get(0);
        Path dir;
        try {
            dir = env.getCurrentDirectory().resolve(dirName);
        } catch (InvalidPathException ex) {
            env.writeln("Invalid path.");
            return ShellStatus.CONTINUE;
        }

        if (Files.isDirectory(dir)) {
            env.writeln("Directory already exists.");
            return ShellStatus.CONTINUE;
        }

        try {
            Files.createDirectories(dir);
        } catch (FileAlreadyExistsException ex) {
            env.writeln("There is already a file with the same name specified, specify a different name for the "
                        + "directory.");
        } catch (IOException ex) {
            env.writeln("Could not create directory: " + dirName);
            return ShellStatus.CONTINUE;
        }

        env.writeln("Directory created: " + dirName);
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
