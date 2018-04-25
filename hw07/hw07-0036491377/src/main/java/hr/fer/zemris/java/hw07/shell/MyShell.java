package hr.fer.zemris.java.hw07.shell;

import hr.fer.zemris.java.hw07.shell.commands.ShellCommand;

import java.util.SortedMap;

public class MyShell {
    public static void main(String[] args) {
        Environment env = new EnvironmentImpl();
        SortedMap<String, ShellCommand> commands = env.commands();
        env.writeln("Welcome to MyShell v 1.0");
        while (true) {
            String in = env.readLine();

            String[] split = in.split(" ", 2);
            String command = split[0].toLowerCase();
            String arguments;
            if (split.length == 2) {
                 arguments = split[1];
            } else {
                arguments = "";
            }
            if (commands.keySet().contains(command)) {
                ShellStatus status = commands.get(command).executeCommand(env, arguments.trim());
                if (status == ShellStatus.TERMINATE) {
                    break;
                }
            } else {
                env.writeln("Not a valid command: " + command);
            }
        }

    }
}
