package hr.fer.zemris.java.hw07.shell.commands;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

import java.nio.charset.Charset;
import java.util.*;

/**
 * This command lists all charsets on the current system.
 *
 * @author matej
 */
public class CharsetsCommand implements ShellCommand {
    /**
     * Name of the command
     */
    private static final String NAME = "charsets";
    /**
     * Description of the command
     */
    private static final List<String> DESC;

    static {
        DESC = new ArrayList<>();
        String[] descArr = {
                "Lists available charsets.",
                "",
                "CHARSET"
        };
        DESC.addAll(Arrays.asList(descArr));
    }

    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        SortedMap<String, Charset> charsets = Charset.availableCharsets();

        for (String cs : charsets.keySet()) {
            env.writeln(cs);
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
