package hr.fer.zemris.java.hw07.shell.commands;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import hr.fer.zemris.java.hw07.shell.Util;
import hr.fer.zemris.java.hw07.shell.namebuilder.MultipleNameBuilder;
import hr.fer.zemris.java.hw07.shell.namebuilder.NameBuilderInfo;
import hr.fer.zemris.java.hw07.shell.namebuilder.NameBuilderInfoImpl;
import hr.fer.zemris.java.hw07.shell.namebuilder.NameBuilderParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

        if (!Files.isDirectory(dir1)) {
            env.writeln("Source directory does not exist: " + dir1);
            return ShellStatus.CONTINUE;
        }

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
                return filter(env, dir1, mask);
            case "groups":
                return group(env, dir1, mask);
            case "show": {
                if (args.size() != 5) {
                    env.writeln("Wrong number of arguments for sub-command");
                    return ShellStatus.CONTINUE;
                }
                String expression = args.get(4);
                return show(env, dir1, dir2, mask, expression);
            }
            case "execute": {
                if (args.size() != 5) {
                    env.writeln("Wrong number of arguments for sub-command");
                    return ShellStatus.CONTINUE;
                }

                if (!Files.isDirectory(dir2)) {
                    env.writeln("Destination directory does not exist: " + dir2);
                    return ShellStatus.CONTINUE;
                }

                String expression = args.get(4);
                return execute(env, dir1, dir2, mask, expression);
            }
            default:
                env.writeln("Non-existent sub-command: " + command);
                return ShellStatus.CONTINUE;
        }
    }

    /**
     * Filters the result with respect to the regex pattern without printing out the capturing groups.
     *
     * @param env environment
     * @param dir1 directory whose files to walk through (non-recursively)
     * @param mask regex pattern used to match the names of the files
     *
     * @return ShellStatus.CONTINUE
     */
    private ShellStatus filter(Environment env, Path dir1, Pattern mask) {
        return writePatternTestResult(env, dir1, mask, false);
    }

    /**
     * Filters the result with respect to the regex pattern while also printing out the capturing groups.
     *
     * @param env environment
     * @param dir1 directory whose files to walk through (non-recursively)
     * @param mask regex pattern used to match the names of the files
     *
     * @return ShellStatus.CONTINUE
     */
    private ShellStatus group(Environment env, Path dir1, Pattern mask) {
        return writePatternTestResult(env, dir1, mask, true);
    }

    /**
     * Writes the results of matching the pattern against the files in the provided directory.
     *
     * @param env environment
     * @param dir1 source directory
     * @param mask pattern mask to match against
     * @param writeGroups flag to decide if the capturing groups should also be included
     *
     * @return ShellStatus.CONTINUE
     */
    private ShellStatus writePatternTestResult(Environment env, Path dir1, Pattern mask, boolean writeGroups) {
        try {
            Files.walk(dir1).forEach((file) -> {
                if (Files.exists(file)) {
                    Matcher m = mask.matcher(file.getFileName().toString());

                    if (m.matches()) {
                        env.write(file.getFileName().toString());

                        if (writeGroups) {
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

    /**
     * This method maps old paths that were matched with new paths to be created when the original files move there.
     *
     * @param dir1 source path
     * @param dir2 destination path
     * @param mask pattern mask
     * @param expression substitution expression
     *
     * @return a HashMap of the pairs of paths
     */
    private HashMap<Path, Path> mapOldToNew(Path dir1, Path dir2, Pattern mask, String expression) {
        HashMap<Path, Path> map = new HashMap<>();

        try {
            NameBuilderParser parser = new NameBuilderParser(expression);
            MultipleNameBuilder builder = parser.getNameBuilder();
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

                Path absoluteSource = dir1.resolve(file);
                Path absoluteDestination = dir2.resolve(newName);
                map.put(absoluteSource, absoluteDestination);
                //env.writeln(String.format("%s => %s", file.toString(), newName));
            });
        } catch (IOException e) {
            throw new IllegalArgumentException("Could not walk directory: " + dir1);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }

        return map;
    }

    /**
     * This method executes the mass renaming show sub-command as described in the description.
     *
     * @param env envrionment
     * @param dir1 source path
     * @param dir2 destination path
     * @param mask pattern mask
     * @param expression substitution expression
     *
     * @return ShellStatus.CONTINUE if no I/O exception occurs between the user and the shell
     */
    private ShellStatus show(Environment env, Path dir1, Path dir2, Pattern mask, String expression) {
        HashMap<Path, Path> map;
        try {
            map = mapOldToNew(dir1, dir2, mask, expression);
        } catch (IllegalArgumentException e) {
            env.writeln(e.getMessage());
            return ShellStatus.CONTINUE;
        }

        for (Map.Entry<Path, Path> entry : map.entrySet()) {
            env.writeln(String.format("%s => %s", dir1.relativize(entry.getKey()), dir2.relativize(entry.getValue())));
        }

        return ShellStatus.CONTINUE;
    }

    /**
     * This method executes the mass renaming command as described in the description.
     *
     * @param env envrionment
     * @param dir1 source path
     * @param dir2 destination path
     * @param mask pattern mask
     * @param expression substitution expression
     *
     * @return ShellStatus.CONTINUE if no I/O exception occurs between the user and the shell
     */
    private ShellStatus execute(Environment env, Path dir1, Path dir2, Pattern mask, String expression) {
        HashMap<Path, Path> map;
        try {
            map = mapOldToNew(dir1, dir2, mask, expression);
        } catch (IllegalArgumentException e) {
            env.writeln(e.getMessage());
            return ShellStatus.CONTINUE;
        }

        for (Map.Entry<Path, Path> entry : map.entrySet()) {
            Path absoluteSource = entry.getKey();
            Path absoluteDestination = entry.getValue();

            try {
                Files.move(absoluteSource, absoluteDestination);
            } catch (IOException e) {
                env.writeln("Could not copy files from " + absoluteSource + " to " + absoluteDestination);
                return ShellStatus.CONTINUE;
            }

            env.writeln(String.format("%s/%s => %s/%s",
                    dir1.getFileName().toString(), dir1.relativize(absoluteSource),
                    dir2.getFileName().toString(), dir2.relativize(absoluteDestination)));
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
