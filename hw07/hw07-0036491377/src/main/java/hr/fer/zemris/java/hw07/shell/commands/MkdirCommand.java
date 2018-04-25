package hr.fer.zemris.java.hw07.shell.commands;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import hr.fer.zemris.java.hw07.shell.Util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class MkdirCommand implements ShellCommand {
    private static final String NAME = "mkdir";

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
            dir = Paths.get(dirName);
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
        } catch (IOException e) {
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
        return null;
    }
}
