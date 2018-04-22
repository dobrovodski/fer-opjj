package hr.fer.zemris.java.hw07.shell.commands;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

import java.util.List;

public interface ShellCommand {
    ShellStatus executeCommand(Environment env, String arguments);
    String getCommandName();
    List<String> getCommandDescription();
}
