package hr.fer.zemris.java.hw07.shell;

import hr.fer.zemris.java.hw07.shell.commands.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * The concrete implementation of {@link Environment}.
 *
 * @author matej
 */
public class EnvironmentImpl implements Environment {
    /**
     * Reader for reading user input.
     */
    private BufferedReader reader;
    /**
     * Writer for writing to the user.
     */
    private BufferedWriter writer;
    /**
     * Multiline symbol.
     */
    private Character multilineSymbol = '|';
    /**
     * Prompt symbol.
     */
    private Character promptSymbol = '>';
    /**
     * More lines symbol.
     */
    private Character morelinesSymbol = '\\';
    /**
     * Sorted map of all commands.
     */
    private static SortedMap<String, ShellCommand> commands;

    private Path currentDirectory;

    private HashMap<String, Object> sharedData;

    public final static String STACK_NAME = "cdstack";

    static {
        commands = new TreeMap<>();

        ShellCommand[] commandsToAdd = {
                new SymbolCommand(),
                new CharsetsCommand(),
                new CatCommand(),
                new CopyCommand(),
                new ExitCommand(),
                new HelpCommand(),
                new HexdumpCommand(),
                new LsCommand(),
                new MkdirCommand(),
                new TreeCommand(),
                new PwdCommand(),
                new CdCommand(),
                new PushdCommand(),
                new PopdCommand(),
                new DropdCommand(),
                new ListdCommand(),
                new RmtreeCommand(),
                new CptreeCommand(),
                new MassrenameCommand()
        };

        for (ShellCommand c : commandsToAdd) {
            commands.put(c.getCommandName(), c);
        }
    }

    /**
     * Default constructor.
     */
    public EnvironmentImpl() {
        reader = new BufferedReader(new InputStreamReader(System.in));
        writer = new BufferedWriter(new OutputStreamWriter(System.out));
        currentDirectory = Paths.get(".").toAbsolutePath().normalize();
        sharedData = new HashMap<>();
    }

    @Override
    public String readLine() throws ShellIOException {
        StringBuilder sb = new StringBuilder();
        // write prompt
        write(String.valueOf(promptSymbol + " "));
        while (true) {
            String next;
            try {
                next = reader.readLine();
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

    @Override
    public Path getCurrentDirectory() {
        return currentDirectory;
    }

    @Override
    public void setCurrentDirectory(Path path) {
        if (Files.notExists(path) || !Files.isDirectory(path)) {
            throw new IllegalArgumentException("This path does not exist or it is not a valid directory: "
                                               + path.toString());
        }

        currentDirectory = path.toAbsolutePath();
    }

    @Override
    public Object getSharedData(String key) {
        return sharedData.get(key);
    }

    @Override
    public void setSharedData(String key, Object value) {
        sharedData.put(key, value);
    }
}
