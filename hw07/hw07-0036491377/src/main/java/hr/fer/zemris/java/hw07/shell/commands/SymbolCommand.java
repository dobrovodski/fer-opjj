package hr.fer.zemris.java.hw07.shell.commands;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

import java.util.List;

public class SymbolCommand implements ShellCommand {
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        String[] args = arguments.split("\\s+");
        int len = args.length;

        if (len < 1 || len > 2) {
            String out = String.format("Invalid number of arguments for command '%s.'", getCommandName());
            env.writeln(out);
            return ShellStatus.CONTINUE;
        }
        if (len == 2 && args[1].length() != 1) {
            env.writeln("Symbols can only be 1 character long");
            return ShellStatus.CONTINUE;
        }

        String sym = args[0].toUpperCase();
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

        char newSym = args[1].charAt(0);
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
        return "symbol";
    }

    @Override
    public List<String> getCommandDescription() {
        return null;
    }
}
