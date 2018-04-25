package hr.fer.zemris.java.hw07.shell.commands;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import hr.fer.zemris.java.hw07.shell.Util;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class CopyCommand implements ShellCommand {
    private static final String NAME = "copy";

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

        File srcFile = new File(srcName);
        File destFile = new File(destName);

        if (destFile.exists()) {
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

        try {
            boolean created = destFile.createNewFile();
            InputStream in = new BufferedInputStream(new FileInputStream(srcFile));
            OutputStream out = new BufferedOutputStream(new FileOutputStream(destFile));

            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }

            in.close();
            out.close();
        } catch (FileNotFoundException e) {
            env.writeln("Source file not found.");
            return ShellStatus.CONTINUE;
        } catch (IOException e) {
            env.writeln("Exception while reading or writing the file.");
            return ShellStatus.CONTINUE;
        }

        env.writeln("File successfully copied.");
        return ShellStatus.CONTINUE;
    }

    @Override
    public String getCommandName() {
        return NAME;
    }

    @Override
    public List<String> getCommandDescription() {
        return null;
    }
}
