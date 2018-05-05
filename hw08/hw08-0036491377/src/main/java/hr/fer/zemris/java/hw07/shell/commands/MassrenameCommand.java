package hr.fer.zemris.java.hw07.shell.commands;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import hr.fer.zemris.java.hw07.shell.Util;
import hr.fer.zemris.java.hw07.shell.namebuilder.MultipleNameBuilder;
import hr.fer.zemris.java.hw07.shell.namebuilder.NameBuilder;
import hr.fer.zemris.java.hw07.shell.namebuilder.NameBuilderInfo;
import hr.fer.zemris.java.hw07.shell.namebuilder.NameBuilderInfoImpl;
import hr.fer.zemris.java.hw07.shell.namebuilder.NameBuilderParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;


/**
 * This command batch renames/moves files within the provided source directory to the destination directory. It supports
 * selecting files using regular expressions and offers certain sub-commands for easier use.
 *
 * @author matej
 */
public class MassrenameCommand implements ShellCommand {
    /**
     * Name of the command
     */
    private static final String NAME = Commands.MASSRENAME.getName();
    /**
     * Description of the command
     */
    private static final List<String> DESC;

    static {
        DESC = new ArrayList<>();
        String[] descArr = {
                "Batch renames/moves files within the provided source directory to the destination directory. It "
                + "supports selecting files using regular expressions and offers certain sub-commands for easier use.",
                "",
                "MASSRENAME [source] [destination] [command] [mask] [expression]",
                "   source - source path to files",
                "",
                "   destination - destination path",
                "",
                "   command - FILTER -- lists files selected by regular expression in [mask]",
                "             GROUPS -- lists groups selected by regular expression in [mask]",
                "             SHOW -- lists pairs of current-name:new-name as defined by [expression] that will be "
                + "used to rename selected files",
                "             EXECUTE -- performs the action of moving/copying the selected files",

                "",
                "   mask - regular expression used in FILTER, GROUPS and SHOW",
                "",
                "   expression - regular expression used in SHOW",
        };
        DESC.addAll(Arrays.asList(descArr));
    }

    /**
     * {@inheritDoc} This command batch renames/moves files within the provided source directory to the destination
     * directory. It supports selecting files using regular expressions and offers certain sub-commands for easier use.
     */
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        List<String> args = Util.split(arguments);

        if (args == null) {
            env.writeln("Space required after ending quote.");
            return ShellStatus.CONTINUE;
        }

        if (args.size() < 3) {
            env.writeln("This command takes at least 4 arguments.");
            return ShellStatus.CONTINUE;
        }

        Path dir1 = env.getCurrentDirectory().resolve(args.get(0));
        Path dir2 = env.getCurrentDirectory().resolve(args.get(1));
        String command = args.get(2);
        Pattern mask;

        try {
            mask = Pattern.compile(args.get(3), Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
        } catch (PatternSyntaxException ex) {
            env.writeln("Invalid regular expression: " + args.get(3));
            return ShellStatus.CONTINUE;
        }

        switch (command.toLowerCase()) {
            case "filter":
                return filter(env, dir1, mask, false);
            case "groups":
                return filter(env, dir1, mask, true);
            case "show": {
                if (args.size() != 5) {
                    env.writeln("Wrong number of arguments for subcommand");
                    return ShellStatus.CONTINUE;
                }
                String expression = args.get(4);
                return show(env, dir1, dir2, mask, expression);
            }
            case "execute": {
                if (args.size() != 5) {
                    env.writeln("Wrong number of arguments for subcommand");
                    return ShellStatus.CONTINUE;
                }
                String expression = args.get(4);
                return execute(env, dir1, dir2, mask, expression);
            }
            default:
                env.writeln("Non-existent sub-command: " + command);
                return ShellStatus.CONTINUE;
        }

        //return ShellStatus.CONTINUE;
    }

    private ShellStatus filter(Environment env, Path dir1, Pattern mask, boolean groups) {
        try {
            Files.walk(dir1).forEach((file) -> {
                if (Files.exists(file)) {
                    Matcher m = mask.matcher(file.getFileName().toString());

                    if (m.matches()) {
                        env.write(file.getFileName().toString());

                        if (groups) {
                            int count = m.groupCount();
                            for (int i = 0; i <= count; i++) {
                                env.write(String.format(" %d: %s", i, m.group(i)));
                            }
                        }

                        env.writeln("");
                    }
                }
            });
        } catch (IOException e) {
            env.writeln("Could not walk through directory: " + dir1);
            return ShellStatus.CONTINUE;
        }

        return ShellStatus.CONTINUE;
    }

    private ShellStatus show(Environment env, Path dir1, Path dir2, Pattern mask, String expression) {
        NameBuilderParser parser = new NameBuilderParser(expression);
        MultipleNameBuilder builder = parser.getNameBuilder();

        try {
            Files.walk(dir1, 1).forEach((file) -> {
                if (!Files.isRegularFile(file)) {
                    return;
                }
                file = dir1.relativize(file);

                Matcher matcher = mask.matcher(file.getFileName().toString());
                if (!matcher.find()) {
                    return;
                }

                NameBuilderInfo info = new NameBuilderInfoImpl(matcher);
                builder.execute(info);
                String newName = info.getStringBuilder().toString();
                env.writeln(String.format("%s => %s", file.toString(), newName));
            });
        } catch (IOException e) {
            env.writeln("Could not walk directory: " + dir1);
            return ShellStatus.CONTINUE;
        }

        return ShellStatus.CONTINUE;
    }

    private ShellStatus execute(Environment env, Path dir1, Path dir2, Pattern mask, String expression) {
        NameBuilderParser parser = new NameBuilderParser(expression);
        MultipleNameBuilder builder = parser.getNameBuilder();

        try {
            Files.walk(dir1, 1).forEach((file) -> {
                if (!Files.isRegularFile(file)) {
                    return;
                }
                file = dir1.relativize(file);

                Matcher matcher = mask.matcher(file.getFileName().toString());
                if (!matcher.find()) {
                    return;
                }

                NameBuilderInfo info = new NameBuilderInfoImpl(matcher);
                builder.execute(info);
                String newName = info.getStringBuilder().toString();
                try {
                    Files.move(file, Paths.get(newName));
                } catch (IOException e) {
                    env.writeln("Could not copy file: " + file.toString() + " to: " + newName);
                }
            });
        } catch (IOException e) {
            env.writeln("Could not walk directory: " + dir1);
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
