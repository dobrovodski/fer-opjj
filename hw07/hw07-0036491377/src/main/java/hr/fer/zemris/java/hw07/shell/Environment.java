package hr.fer.zemris.java.hw07.shell;

import hr.fer.zemris.java.hw07.shell.commands.ShellCommand;

import java.util.SortedMap;

public interface Environment {
    String readLine() throws ShellIOException;
    void write(String text) throws ShellIOException;
    void writeln(String text) throws ShellIOException;
    SortedMap<String, ShellCommand> commands();
    Character getMultilineSymbol();
    void setMultilineSymbol(Character symbol);
    Character getPromptSymbol();
    void setPromptSymbol(Character symbol);
    Character getMorelinesSymbol();
    void setMorelinesSymbol(Character symbol);
}
