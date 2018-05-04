package hr.fer.zemris.java.hw07.shell.commands;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


/**
 * This command prints the current working directory.
 *
 * @author matej
 */
public class PwdCommand implements ShellCommand {
    /**
     * Name of the command
     */
    private static final String NAME = Commands.PWD.getName();
    /**
     * Description of the command
     */
    private static final List<String> DESC;

    static {
        DESC = new ArrayList<>();
        String[] descArr = {
                "Prints the current working directory.",
                "",
                "PWD",
        };
        DESC.addAll(Arrays.asList(descArr));
    }

    /**
     * {@inheritDoc} This command prints the current working directory.
     */
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        env.writeln(env.getCurrentDirectory().toString());

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
