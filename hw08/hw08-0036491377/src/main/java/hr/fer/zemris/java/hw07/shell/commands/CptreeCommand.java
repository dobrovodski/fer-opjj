package hr.fer.zemris.java.hw07.shell.commands;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.EnvironmentImpl;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import hr.fer.zemris.java.hw07.shell.Util;

import java.nio.file.Path;
import java.util.*;


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
