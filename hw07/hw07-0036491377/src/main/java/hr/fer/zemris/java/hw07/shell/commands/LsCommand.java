package hr.fer.zemris.java.hw07.shell.commands;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellIOException;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LsCommand implements ShellCommand {
    private static final String NAME = "ls";
    private String regex = "[^\\s\"']+|\"([^\"]*)\"|'([^']*)'";
    private Pattern p = Pattern.compile(regex);

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

    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        Matcher m = p.matcher(arguments);
        if (!m.find()) {
            env.writeln("Invalid number of parameters.");
            return ShellStatus.CONTINUE;
        }

        String dirName;
        if (m.group(1) != null) {
            dirName = m.group(1);
        } else if (m.group(2) != null) {
            dirName = m.group(2);
        } else {
            dirName = m.group();
        }

        Path dir = Paths.get(dirName);
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
