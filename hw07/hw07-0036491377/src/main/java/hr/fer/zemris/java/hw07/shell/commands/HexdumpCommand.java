package hr.fer.zemris.java.hw07.shell.commands;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import hr.fer.zemris.java.hw07.shell.Util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class HexdumpCommand implements ShellCommand {
    private static final String NAME = "hexdump";
    private static final int ROW_LEN = 16;

    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        List<String> args = Util.split(arguments);

        if (args == null) {
            env.writeln("Space required after ending quote.");
            return ShellStatus.CONTINUE;
        }

        if (args.size() < 1) {
            env.writeln("This command requires parameters.");
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

        byte[] bytes;
        try {
            bytes = Files.readAllBytes(dir);
        } catch (IOException e) {
            env.writeln("Could not read file.");
            return ShellStatus.CONTINUE;
        }

        StringBuilder leftEight = new StringBuilder();
        StringBuilder rightEight = new StringBuilder();
        StringBuilder converted = new StringBuilder();

        for (int i = 0; i < (double) bytes.length / ROW_LEN; i++) {
            for (int j = 0; j < ROW_LEN; j++) {
                if (i * ROW_LEN + j > bytes.length - 1) {
                    break;
                }
                if (bytes[i * ROW_LEN + j] >= 32 && bytes[i * ROW_LEN + j] < 127) {
                    converted.append(String.valueOf((char) bytes[i * ROW_LEN + j]));
                } else {
                    converted.append(".");
                }

                String fullNum = String.format("%2s", Integer.toHexString(bytes[i * ROW_LEN + j])).replace(" ", "0");
                String num = fullNum.substring(fullNum.length() - 2, fullNum.length()); // Cut off Fs for negatives

                if (j < ROW_LEN / 2) {
                    leftEight.append(num).append(" ");
                } else {
                    rightEight.append(num).append(" ");
                }
            }

            String rowNum = String.format("%8s", Integer.toHexString(i * ROW_LEN)).replace(" ", "0").toUpperCase();
            String firstHex = padRightWithSpace(leftEight.toString().trim().toUpperCase(), ROW_LEN + (ROW_LEN / 2 - 1));
            String secondHex = padRightWithSpace(rightEight.toString().trim().toUpperCase(), ROW_LEN + (ROW_LEN / 2 - 1));
            String out = String.format("%8s: %s|%s | %s", rowNum, firstHex, secondHex, converted.toString());

            // Reset StringBuilders
            leftEight.setLength(0);
            rightEight.setLength(0);
            converted.setLength(0);

            env.writeln(out);
        }

        return ShellStatus.CONTINUE;
    }

    private String padRightWithSpace(String str, int len) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        while (sb.length() < len) {
            sb.append(" ");
        }
        return sb.toString();
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
