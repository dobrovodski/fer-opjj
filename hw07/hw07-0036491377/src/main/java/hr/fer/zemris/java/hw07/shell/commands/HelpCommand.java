package hr.fer.zemris.java.hw07.shell.commands;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import hr.fer.zemris.java.hw07.shell.Util;

import java.util.*;

/**
 * This command displays all commands or provides help about selected command.
 *
 * @author matej
 */
public class HelpCommand implements ShellCommand {
    /**
     * Name of the command
     */
    private static final String NAME = "help";
    /**
     * Description of the command
     */
    private static final List<String> DESC;

    static {
        DESC = new ArrayList<>();
        String[] descArr = {
                "Provides help information for commands or lists all existing commands if no argument is provided.",
                "",
                "HELP",
                "HELP [command]",
                "",
                "   command - displays help information on that command."
        };
        DESC.addAll(Arrays.asList(descArr));
    }

    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        List<String> args = Util.split(arguments);

        if (args == null) {
            env.writeln("Space required after ending quote.");
            return ShellStatus.CONTINUE;
        }

        if (args.size() > 1) {
            env.writeln("Too many parameters - " + args.get(1));
            return ShellStatus.CONTINUE;
        }

        SortedMap<String, ShellCommand> commands = env.commands();
        if (args.size() == 0) {
            for (String command : commands.keySet()) {
                env.writeln(command);
            }
            return ShellStatus.CONTINUE;
        }

        ShellCommand command = commands.get(args.get(0));
        if (command == null) {
            env.writeln("Command doesn't exist.");
            return ShellStatus.CONTINUE;
        }
        for (String s : command.getCommandDescription()) {
            env.writeln(s);
        }

        return null;
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
