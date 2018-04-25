package hr.fer.zemris.java.hw07.shell.commands;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import hr.fer.zemris.java.hw07.shell.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SymbolCommand implements ShellCommand {
    private static final String NAME = "symbol";
    private static final List<String> DESC;

    static {
        DESC = new ArrayList<>();
        String[] descArr = {
                "Displays selected symbol or changes it.",
                "",
                "SYMBOL [symbol]",
                "SYMBOL [symbol] [replacement]",
                "",
                "   symbol - selected symbol (PROMPT, MULTILINE, MORELINES).",
                "   replacement - character to replace selected symbol with."
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

        int len = args.size();
        if (len == 0 || len > 2) {
            env.writeln("Invalid number of arguments");
            return ShellStatus.CONTINUE;
        }

        if (len == 2 && args.get(1).length() != 1) {
            env.writeln("Symbols can only be 1 character long");
            return ShellStatus.CONTINUE;
        }

        String sym = args.get(0).toUpperCase();
        char curSym;
        switch (sym) {
            case "PROMPT":
                curSym = env.getPromptSymbol();
                break;
            case "MORELINES":
                curSym = env.getMorelinesSymbol();
                break;
            case "MULTILINE":
                curSym = env.getMultilineSymbol();
                break;
            default:
                env.writeln("Invalid symbol.");
                return ShellStatus.CONTINUE;
        }

        if (len == 1) {
            String out = String.format("Symbol for %s is '%c'.", sym, curSym);
            env.writeln(out);
            return ShellStatus.CONTINUE;
        }

        char newSym = args.get(1).charAt(0);
        String out = String.format("Symbol for %s changed from '%c' to '%c'.", sym, curSym, newSym);
        env.writeln(out);

        switch (sym) {
            case "PROMPT":
                env.setPromptSymbol(newSym);
                break;
            case "MORELINES":
                env.setMorelinesSymbol(newSym);
                break;
            case "MULTILINE":
                env.setMultilineSymbol(newSym);
                break;
            default:
                env.writeln("Invalid symbol.");
                return ShellStatus.CONTINUE;
        }

        return ShellStatus.CONTINUE;
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
