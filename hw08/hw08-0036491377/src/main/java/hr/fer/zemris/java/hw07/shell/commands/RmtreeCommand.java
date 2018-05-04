package hr.fer.zemris.java.hw07.shell.commands;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import hr.fer.zemris.java.hw07.shell.Util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * This command deletes the given directory structure.
 *
 * @author matej
 */
public class RmtreeCommand implements ShellCommand {
    /**
     * Name of the command
     */
    private static final String NAME = Commands.RMTREE.getName();
    /**
     * Description of the command
     */
    private static final List<String> DESC;

    static {
        DESC = new ArrayList<>();
        String[] descArr = {
                "Deletes the given directory.",
                "",
                "RMTREE [path]",
                "",
                "   path - path of directory to remove"
        };
        DESC.addAll(Arrays.asList(descArr));
    }

    /**
     * {@inheritDoc} This command deletes the given directory structure.
     */
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        List<String> args = Util.split(arguments);

        if (args == null) {
            env.writeln("Space required after ending quote.");
            return ShellStatus.CONTINUE;
        }

        if (args.size() != 1) {
            env.writeln("This command takes a single argument.");
            return ShellStatus.CONTINUE;
        }

        Path directory = env.getCurrentDirectory().resolve(args.get(0));

        if (!Files.isDirectory(directory) || Files.notExists(directory)) {
            env.writeln("Not a valid directory.");
            return ShellStatus.CONTINUE;
        }

        try {
            // Recursively delete all files first.
            // Can't delete a directory which is not empty.
            Files.walk(directory)
                 .sorted(Comparator.reverseOrder())
                 .forEach((p) -> {
                     try {
                         env.writeln("Deleting: " + p.toString());
                         Files.deleteIfExists(p);
                     } catch (IOException e) {
                         env.writeln("Could not delete file: " + p.toString());
                     }
                 });
        } catch (IOException e) {
            env.writeln("Could not access directory.");
            return ShellStatus.CONTINUE;
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
