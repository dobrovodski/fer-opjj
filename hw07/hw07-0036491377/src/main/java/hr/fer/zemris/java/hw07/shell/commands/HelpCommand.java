package hr.fer.zemris.java.hw07.shell.commands;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

import java.util.List;
import java.util.SortedMap;

public class HelpCommand implements ShellCommand {
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        String[] args = arguments.split("\\s+");

        if (args.length > 1) {
            env.writeln("Help command takes up to 1 argument.");
            return ShellStatus.CONTINUE;
        }

        SortedMap<String, ShellCommand> commands = env.commands();
        if (args.length == 0) {

        }
        return null;
    }

    @Override
    public String getCommandName() {
        return "help";
    }

    @Override
    public List<String> getCommandDescription() {
        return null;
    }
}
