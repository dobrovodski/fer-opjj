package hr.fer.zemris.java.hw07.shell.commands;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

import java.nio.charset.Charset;
import java.util.List;
import java.util.SortedMap;

public class CharsetsCommand implements ShellCommand {
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        SortedMap<String, Charset> charsets = Charset.availableCharsets();

        for (SortedMap.Entry<String, Charset> cs : charsets.entrySet()) {
            env.writeln(cs.getKey());
        }

        return ShellStatus.CONTINUE;
    }

    @Override
    public String getCommandName() {
        return "charsets";
    }

    @Override
    public List<String> getCommandDescription() {
        return null;
    }
}
