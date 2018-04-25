package hr.fer.zemris.java.hw07.shell;

import hr.fer.zemris.java.hw07.shell.commands.*;

import java.io.*;
import java.util.*;

public class EnvironmentImpl implements Environment {
    private BufferedReader sc;
    private BufferedWriter writer;
    private Character multilineSymbol = '|';
    private Character promptSymbol = '>';
    private Character morelinesSymbol = '\\';
    private static SortedMap<String, ShellCommand> commands;

    static {
        commands = new TreeMap<>();

        ShellCommand c;
        c = new SymbolCommand();
        commands.put(c.getCommandName(), c);

        c = new CharsetsCommand();
        commands.put(c.getCommandName(), c);

        c = new CatCommand();
        commands.put(c.getCommandName(), c);

        c = new CopyCommand();
        commands.put(c.getCommandName(), c);

        c = new ExitCommand();
        commands.put(c.getCommandName(), c);

        c = new HelpCommand();
        commands.put(c.getCommandName(), c);

        c = new HexdumpCommand();
        commands.put(c.getCommandName(), c);

        c = new LsCommand();
        commands.put(c.getCommandName(), c);

        c = new MkdirCommand();
        commands.put(c.getCommandName(), c);

        c = new TreeCommand();
        commands.put(c.getCommandName(), c);
    }

    public EnvironmentImpl() {
        sc = new BufferedReader(new InputStreamReader(System.in));
        writer = new BufferedWriter(new OutputStreamWriter(System.out));
    }

    @Override
    public String readLine() throws ShellIOException {
        StringBuilder sb = new StringBuilder();
        // write prompt
        write(String.valueOf(promptSymbol + " "));
        while (true) {
            String next;
            try {
                next = sc.readLine();
            } catch (IOException e) {
                throw new ShellIOException("Could not read line.");
            }

            sb.append(next);
            if (!next.endsWith(String.valueOf(morelinesSymbol))) {
                break;
            }
            // write multilineSymbol
            write(String.valueOf(multilineSymbol + " "));
            // Remove morelinesSymbol
            sb.setLength(sb.length() - 1);
        }

        return sb.toString();
    }

    @Override
    public void write(String text) throws ShellIOException {
        try {
            writer.write(text);
            writer.flush();
        } catch (IOException e) {
            throw new ShellIOException("Could not write to standard output.");
        }
    }

    @Override
    public void writeln(String text) throws ShellIOException {
        // This is in try-catch because newLine can also throw an exception
        try {
            write(text);
            writer.newLine();
        } catch (IOException e) {
            throw new ShellIOException("Could not write to standard output");
        }
    }

    @Override
    public SortedMap<String, ShellCommand> commands() {
        return Collections.unmodifiableSortedMap(commands);
    }

    @Override
    public Character getMultilineSymbol() {
        return multilineSymbol;
    }

    @Override
    public void setMultilineSymbol(Character symbol) {
        this.multilineSymbol = symbol;
    }

    @Override
    public Character getPromptSymbol() {
        return promptSymbol;
    }

    @Override
    public void setPromptSymbol(Character symbol) {
        this.promptSymbol = symbol;
    }

    @Override
    public Character getMorelinesSymbol() {
        return morelinesSymbol;
    }

    @Override
    public void setMorelinesSymbol(Character symbol) {
        this.morelinesSymbol = symbol;
    }
}
