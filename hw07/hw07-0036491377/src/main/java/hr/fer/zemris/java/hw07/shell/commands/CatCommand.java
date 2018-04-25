package hr.fer.zemris.java.hw07.shell.commands;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import hr.fer.zemris.java.hw07.shell.Util;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.MalformedInputException;
import java.nio.charset.UnsupportedCharsetException;
import java.nio.file.*;
import java.util.List;
import java.util.stream.Stream;

public class CatCommand implements ShellCommand {
    private static final String NAME = "cat";

    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        List<String> args = Util.split(arguments);

        if (args == null) {
            env.writeln("Space required after ending quote.");
            return ShellStatus.CONTINUE;
        }

        if (args.size() < 1) {
            env.writeln("This command requires parameters.");
            return ShellStatus.CONTINUE;
        }

        if (args.size() > 2) {
            env.writeln("Too many parameters.");
            return ShellStatus.CONTINUE;
        }

        String dirName = args.get(0);
        Charset cs;
        if (args.size() == 2) {
            try {
                cs = Charset.forName(args.get(1));
            } catch (IllegalCharsetNameException | UnsupportedCharsetException e) {
                env.writeln("Could not find given charset.");
                return ShellStatus.CONTINUE;
            }
        } else {
            cs = Charset.defaultCharset();
            env.writeln("Using default charset: " + cs.toString());
        }

        Path dir;
        try {
            dir = Paths.get(dirName);
        } catch (InvalidPathException ex) {
            env.writeln("Invalid path.");
            return ShellStatus.CONTINUE;
        }

        if (!Files.isRegularFile(dir)) {
            env.writeln("Can only read files.");
            return ShellStatus.CONTINUE;
        }

        try {
            Stream<String> lines = Files.lines(dir, cs);
            lines.forEach(env::writeln);
        } catch (UncheckedIOException e) {
            env.writeln("Malformed input - wrong charset.");
            return ShellStatus.CONTINUE;
        } catch (IOException e) {
            env.writeln("Could not read file.");
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
