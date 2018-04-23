package hr.fer.zemris.java.hw07.shell.commands;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellIOException;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LsCommand implements ShellCommand {
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        String regex = "[^\\s\"']+|\"([^\"]*)\"|'([^']*)'";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(arguments);
        if (!m.find()) {
            env.writeln("Couldn't run command.");
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

        File dir = new File(dirName);
        File[] files = dir.listFiles();
        if (files == null) {
            env.writeln("Could not read this directory: " + dir.toString());
            return ShellStatus.CONTINUE;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (File file : files) {
            Path path = Paths.get(file.getAbsolutePath());
            BasicFileAttributeView faView = Files.getFileAttributeView(
                    path, BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS
            );
            BasicFileAttributes attributes;
            try {
                attributes = faView.readAttributes();
            } catch (IOException e) {
                throw new ShellIOException("Could not read attribute of file: " + path.toString());
            }
            FileTime fileTime = attributes.creationTime();

            String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));
            String size = String.valueOf(file.length());
            String d = file.isDirectory() ? "d" : "-";
            String r = file.canRead() ? "r" : "-";
            String w = file.canWrite() ? "w" : "-";
            String x = file.canExecute() ? "x": "-";
            String name = file.getName();

            String formatted = String.format("%s%s%s%s %9s %s %s", d, r, w, x, size, formattedDateTime, name);
            env.writeln(formatted);
        }

        return ShellStatus.CONTINUE;
    }

    @Override
    public String getCommandName() {
        return "ls";
    }

    @Override
    public List<String> getCommandDescription() {
        return null;
    }
}
