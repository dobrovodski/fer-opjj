package hr.fer.zemris.java.hw07.shell.commands;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import hr.fer.zemris.java.hw07.shell.Util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * This command copies the selected file to the given destination and asks for permission to overwrite a possibly
 * existent file.
 *
 * @author matej
 */
public class CopyCommand implements ShellCommand {
    /**
     * Name of the command
     */
    private static final String NAME = "copy";
    /**
     * Description of the command
     */
    private static final List<String> DESC;

    static {
        DESC = new ArrayList<>();
        String[] descArr = {
                "Copies file from source to given destination. If the destination file exists, it asks for permission"
                + " to overwrite the existent file.",
                "",
                "COPY [source] [destination]",
                "",
                "   source - source file to copy",
                "   destination - location to copy to"
        };
        DESC.addAll(Arrays.asList(descArr));
    }

    /**
     * {@inheritDoc} This command copies the selected file to the given destination and asks for permission to overwrite
     * a possibly existent file. If the destination is a folder, the file is copied into it with the same name.
     */
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        List<String> args = Util.split(arguments);

        if (args == null) {
            env.writeln("Space required after ending quote.");
            return ShellStatus.CONTINUE;
        }

        if (args.size() != 2) {
            env.writeln("This command requires two parameters.");
            return ShellStatus.CONTINUE;
        }

        String srcName = args.get(0);
        String destName = args.get(1);

        Path srcPath = Paths.get(srcName);
        Path destPath = Paths.get(destName);

        if (Files.isDirectory(srcPath)) {
            env.writeln("Can only copy files, not directories.");
            return ShellStatus.CONTINUE;
        }

        if (Files.exists(destPath) && Files.isRegularFile(destPath)) {
            env.writeln("Destination file exists. Overwrite? (Y/N): ");
            String decision = env.readLine().trim();
            if (decision.length() > 1) {
                return ShellStatus.CONTINUE;
            }
            if (!decision.toUpperCase().equals("Y")) {
                env.writeln("File copy unsuccessful.");
                return ShellStatus.CONTINUE;
            }
        }

        if (Files.isDirectory(destPath)) {
            env.writeln("Copying file to directory: " + destPath);
            destPath = Paths.get(destName + "/" + srcName);
        }

        try {
            Files.createFile(destPath);
            InputStream in = new BufferedInputStream(Files.newInputStream(srcPath));
            OutputStream out = new BufferedOutputStream(Files.newOutputStream(destPath));

            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }

            in.close();
            out.close();
            env.writeln("File successfully copied.");
        } catch (FileNotFoundException e) {
            env.writeln("File not found.");
            return ShellStatus.CONTINUE;
        } catch (IOException e) {
            env.writeln("Exception occurred while reading or writing the file.");
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
