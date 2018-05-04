package hr.fer.zemris.java.hw07.shell.commands;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import hr.fer.zemris.java.hw07.shell.Util;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * This command copies a directory structure into the destination.
 *
 * @author matej
 */
public class CptreeCommand implements ShellCommand {
    /**
     * Name of the command
     */
    private static final String NAME = Commands.CPTREE.getName();
    /**
     * Description of the command
     */
    private static final List<String> DESC;

    static {
        DESC = new ArrayList<>();
        String[] descArr = {
                "Copies a directory structure into the destination.",
                "",
                "CPTREE [source] [destination]",
                "",
                "   source - path to directory to copy",
                "   destination - destination path to copy to"
        };
        DESC.addAll(Arrays.asList(descArr));
    }

    /**
     * {@inheritDoc} This command copies a directory structure into the destination.
     */
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        List<String> args = Util.split(arguments);

        if (args == null) {
            env.writeln("Space required after ending quote.");
            return ShellStatus.CONTINUE;
        }

        if (args.size() != 2) {
            env.writeln("This command requires two arguments.");
            return ShellStatus.CONTINUE;
        }

        Path src = env.getCurrentDirectory().resolve(args.get(0));
        Path dest = env.getCurrentDirectory().resolve(args.get(1));

        if (!Files.isDirectory(src)) {
            env.writeln("Not a directory: " + src.toString());
            return ShellStatus.CONTINUE;
        }

        if (Files.isDirectory(dest)) {
            try {
                Path rel = dest.resolve(src.getFileName());
                Files.createDirectory(rel);
                dest = rel;
            } catch (IOException e) {
                env.writeln("Could not create subdirectory. There might already exist a subdirectory with the same "
                            + "name: " + dest.toString());
                return ShellStatus.CONTINUE;
            }
        } else {
            Path parent = dest.getParent();

            if (!Files.isDirectory(parent)) {
                env.writeln("Not a directory: " + dest.toString());
                return ShellStatus.CONTINUE;
            }

            try {
                Files.createDirectory(dest);
            } catch (IOException e) {
                env.writeln("Could not create directory: " + dest.toString());
                return ShellStatus.CONTINUE;
            }
        }

        ShellCommand copyCommand = env.commands().get(Commands.COPY.getName());

        try {
            Path finalDest = dest;
            Files.walkFileTree(src, new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    Files.createDirectories(finalDest.resolve(src.relativize(dir)));
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    Path dest = finalDest.resolve(src.relativize(file));
                    copyCommand.executeCommand(env, String.format("\"%s\" \"%s\"", file, dest));
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            env.writeln("Could not walk given directory.");
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
