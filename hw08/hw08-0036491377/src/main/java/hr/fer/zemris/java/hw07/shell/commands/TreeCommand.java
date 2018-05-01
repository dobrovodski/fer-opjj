package hr.fer.zemris.java.hw07.shell.commands;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import hr.fer.zemris.java.hw07.shell.Util;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * This command graphically displays the folder structure of the selected path.
 *
 * @author matej
 */
public class TreeCommand implements ShellCommand {
    /**
     * Name of the command
     */
    private static final String NAME = "tree";
    /**
     * Description of the command
     */
    private static final List<String> DESC;

    static {
        DESC = new ArrayList<>();
        String[] descArr = {
                "Graphically displays the folder structure of a drive or path.",
                "",
                "TREE [path]",
                "",
                "   path - path to the root file of the tree."
        };
        DESC.addAll(Arrays.asList(descArr));
    }

    /**
     * {@inheritDoc} This command graphically displays the folder structure of the selected path.
     */
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {

        List<String> args = Util.split(arguments);

        if (args == null) {
            env.writeln("Space required after ending quote.");
            return ShellStatus.CONTINUE;
        }

        // By default look at current directory
        // NOTE - this is not in the instructions, but I think it should be this way :)
        if (args.size() == 0) {
            args.add(".");
        }

        // this is how cmd behaves when you have too many parameters
        if (args.size() != 1) {
            env.writeln("Too many parameters - " + args.get(1));
            return ShellStatus.CONTINUE;
        }

        String dirName = args.get(0);

        Path dir;
        try {
            dir = Paths.get(dirName);
        } catch (InvalidPathException ex) {
            env.writeln("Invalid path.");
            return ShellStatus.CONTINUE;
        }

        try {
            Files.walkFileTree(dir, new FileVisitor<>() {
                int depth = 0;

                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                    depth += 2;
                    if (depth != 2) {
                        env.writeln(String.format("%" + depth + "s%s", " ", dir.getFileName()));
                    }
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    env.writeln(String.format("%" + (depth + 2) + "s%s", " ", file.getFileName()));
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) {
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
                    depth -= 2;
                    return FileVisitResult.CONTINUE;
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
