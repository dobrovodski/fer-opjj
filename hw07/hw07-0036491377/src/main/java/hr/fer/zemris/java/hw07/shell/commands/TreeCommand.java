package hr.fer.zemris.java.hw07.shell.commands;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TreeCommand implements ShellCommand {
    private static final String NAME = "tree";
    private String regex = "[^\\s\"']+|\"([^\"]*)\"|'([^']*)'";
    private Pattern p = Pattern.compile(regex);

    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        Matcher m = p.matcher(arguments);
        if (!m.find()) {
            env.writeln("Invalid number of parameters.");
            return ShellStatus.CONTINUE;
        }

        String dirName;
        if (m.group(1) != null) {
            dirName = m.group(1);
        } else if (m.group(2) != null) {
            dirName = m.group(2);
        } else {
            dirName = m.group();
        }

        Path dir = Paths.get(dirName);

        try {
            Files.walkFileTree(dir, new FileVisitor<>() {
                int depth = 0;

                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                    depth += 2;
                    env.writeln(String.format("%" + depth + "s%s", " ", dir.getFileName()));
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
