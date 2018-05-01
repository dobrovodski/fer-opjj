package hr.fer.zemris.java.hw07.shell.commands;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellIOException;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import hr.fer.zemris.java.hw07.shell.Util;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * This command lists directory contents.
 *
 * @author matej
 */
public class LsCommand implements ShellCommand {
    /**
     * Name of the command
     */
    private static final String NAME = "ls";
    /**
     * Description of the command
     */
    private static final List<String> DESC;

    static {
        DESC = new ArrayList<>();
        String[] descArr = {
                "List directory contents.",
                "",
                "LS [path]",
                "",
                "   path - path to directory."
        };
        DESC.addAll(Arrays.asList(descArr));
    }

    /**
     * {@inheritDoc} This command lists directory contents.
     */
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        List<String> args = Util.split(arguments);

        if (args == null) {
            env.writeln("Space required after ending quote.");
            return ShellStatus.CONTINUE;
        }

        // By default look at current directory
        // NOTE - this is not in the instructions, but I think it should be this way :)
        if (args.size() == 0) {
            args.add(".");
        }

        if (args.size() != 1) {
            env.writeln("Too many parameters - " + args.get(1));
            return ShellStatus.CONTINUE;
        }

        String dirName = args.get(0);

        Path dir;
        try {
            dir = Paths.get(dirName);
        } catch (InvalidPathException ex) {
            env.writeln("Invalid path.");
            return ShellStatus.CONTINUE;
        }

        DirectoryStream<Path> files;
        try {
            files = Files.newDirectoryStream(dir);
        } catch (IOException e) {
            env.writeln("Could not read directory.");
            return ShellStatus.CONTINUE;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (Path file : files) {
            BasicFileAttributeView faView = Files.getFileAttributeView(
                    file, BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS
            );
            BasicFileAttributes attributes;
            try {
                attributes = faView.readAttributes();
            } catch (IOException e) {
                throw new ShellIOException("Could not read attribute of file: " + file.toString());
            }
            FileTime fileTime = attributes.creationTime();

            String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));
            String size;
            try {
                size = String.valueOf(Files.size(file));
            } catch (IOException e) {
                size = "?";
            }
            String d = Files.isDirectory(file) ? "d" : "-";
            String r = Files.isReadable(file) ? "r" : "-";
            String w = Files.isWritable(file) ? "w" : "-";
            String x = Files.isExecutable(file) ? "x" : "-";
            String name = file.getFileName().toString();

            String formatted = String.format("%s%s%s%s %9s %s %s", d, r, w, x, size, formattedDateTime, name);
            env.writeln(formatted);
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
