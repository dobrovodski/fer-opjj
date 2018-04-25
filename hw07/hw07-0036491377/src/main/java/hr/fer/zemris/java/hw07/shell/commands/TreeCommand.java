package hr.fer.zemris.java.hw07.shell.commands;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import hr.fer.zemris.java.hw07.shell.Util;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TreeCommand implements ShellCommand {
    private static final String NAME = "tree";

    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {

        List<String> args = Util.split(arguments);
        if (args.size() != 1) {
            env.writeln("Invalid number of arguments.");
            return ShellStatus.CONTINUE;
        }

        String dirName = args.get(0);

        Path dir = Paths.get(dirName);
        if (!Files.isDirectory(dir)) {
            env.writeln("Invalid path - " + dirName + "\n");
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
        return null;
    }
}
