package hr.fer.zemris.java.hw07.shell.commands;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import hr.fer.zemris.java.hw07.shell.Util;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

/**
 * This command opens a file and writes its content to the standard output using provided charset.
 *
 * @author matej
 */
public class CatCommand implements ShellCommand {
    /**
     * Name of the command
     */
    private static final String NAME = Commands.CAT.getName();
    /**
     * Description of the command
     */
    private static final List<String> DESC;

    static {
        DESC = new ArrayList<>();
        String[] descArr = {
                "Opens given file and writes its content to the standard output using the provided charset. If no "
                + "charset is provided, it uses the default charset of the system.",
                "",
                "CAT",
                "CAT [charset]",
                "",
                "   charset - charset to use.",
        };
        DESC.addAll(Arrays.asList(descArr));
    }

    /**
     * {@inheritDoc} This command opens a file and writes its content to the standard output using provided charset.
     */
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
            dir = env.getCurrentDirectory().resolve(dirName);
        } catch (InvalidPathException ex) {
            env.writeln("Invalid path.");
            return ShellStatus.CONTINUE;
        }

        if (!Files.isRegularFile(dir)) {
            env.writeln("Can only read files.");
            return ShellStatus.CONTINUE;
        }

        try {
            // Files#lines does NOT put the entire file into memory, so it is okay to use it
            // Internally, it uses a BufferedReader
            Stream<String> lines = Files.lines(dir, cs);
            lines.forEach(env::writeln);
            lines.close();
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
        return Collections.unmodifiableList(DESC);
    }
}
