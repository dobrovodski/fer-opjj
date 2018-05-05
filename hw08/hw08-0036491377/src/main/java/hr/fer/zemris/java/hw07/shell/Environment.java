package hr.fer.zemris.java.hw07.shell;

import hr.fer.zemris.java.hw07.shell.commands.ShellCommand;

import java.nio.file.Path;
import java.util.SortedMap;

/**
 * This interface provides methods for communication between the shell and the user. Every command needs to have a
 * reference to the environment.
 *
 * @author matej
 */
public interface Environment {
    /**
     * Reads a line from the standard input.
     *
     * @return the line read as a string
     *
     * @throws ShellIOException if unable to read line from input
     */
    String readLine() throws ShellIOException;

    /**
     * Writes given text to the standard output.
     *
     * @param text text to write
     *
     * @throws ShellIOException if unable to write to output
     */
    void write(String text) throws ShellIOException;

    /**
     * Writes given text to the standard output and goes to the next line.
     *
     * @param text text to write
     *
     * @throws ShellIOException if unable to write to output
     */
    void writeln(String text) throws ShellIOException;

    /**
     * Returns an unmodifiable sorted map of the commands in the environment.
     *
     * @return unmodifiable sorted map of the commands in the environment
     */
    SortedMap<String, ShellCommand> commands();

    /**
     * Returns the current multiline symbol.
     *
     * @return current multiline symbol
     */
    Character getMultilineSymbol();

    /**
     * Sets the multiline symbol to the provided one.
     *
     * @param symbol character to set the multiline symbol to
     */
    void setMultilineSymbol(Character symbol);

    /**
     * Returns the current prompt symbol.
     *
     * @return current prompt symbol
     */
    Character getPromptSymbol();

    /**
     * Sets the prompt symbol to the provided one.
     *
     * @param symbol character to set the prompt symbol to
     */
    void setPromptSymbol(Character symbol);

    /**
     * Returns the current morelines symbol.
     *
     * @return current morelines symbol
     */
    Character getMorelinesSymbol();

    /**
     * Sets the morelines symbol to the provided one.
     *
     * @param symbol character to set the morelines symbol to
     */
    void setMorelinesSymbol(Character symbol);

    /**
     * Returns the current directory of the shell.
     *
     * @return current directory of the shell
     */
    Path getCurrentDirectory();

    /**
     * Sets the current directory of the shell.
     *
     * @param path path of the new directory
     */
    void setCurrentDirectory(Path path);

    /**
     * Returns the data stored under the provided key.
     *
     * @param key key to the data
     *
     * @return data found stored at provided key
     */
    Object getSharedData(String key);

    /**
     * Sets the shared data for the given key.
     *
     * @param key key to store the data under
     * @param value data to store
     */
    void setSharedData(String key, Object value);
}
