package hr.fer.zemris.java.hw07.shell.commands;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

import java.util.*;

public class HelpCommand implements ShellCommand {
    private static final String NAME = "help";
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
        String[] args = arguments.split("\\s+");

        if (args.length > 1) {
            env.writeln("Help command takes up to 1 argument.");
            return ShellStatus.CONTINUE;
        }

        SortedMap<String, ShellCommand> commands = env.commands();
        if (arguments.isEmpty()) {
            for (String command : commands.keySet()) {
                env.writeln(command);
            }
            return ShellStatus.CONTINUE;
        }

        ShellCommand command = commands.get(args[0]);
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
