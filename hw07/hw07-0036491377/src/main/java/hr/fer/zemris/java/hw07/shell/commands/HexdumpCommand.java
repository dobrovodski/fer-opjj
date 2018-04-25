package hr.fer.zemris.java.hw07.shell.commands;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import hr.fer.zemris.java.hw07.shell.Util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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
        byte[] bytes = new byte[ROW_LEN];

        try (InputStream is = new BufferedInputStream(new FileInputStream(dirName))) {
            StringBuilder leftEight = new StringBuilder();
            StringBuilder rightEight = new StringBuilder();
            StringBuilder converted = new StringBuilder();

            int count;
            int row = 0;
            while ((count = is.read(bytes, 0, ROW_LEN)) != -1) {
                for (int i = 0; i < count; i++) {
                    if (bytes[i] >= 32 && bytes[i] < 127) {
                        converted.append(String.valueOf((char) bytes[i]));
                    } else {
                        converted.append(".");
                    }

                    String fullNum = String.format("%2s", Integer.toHexString(bytes[i])).replace(" ", "0");
                    String num = fullNum.substring(fullNum.length() - 2, fullNum.length()); // Cut off Fs for negatives
                    if (i < ROW_LEN / 2) {
                        leftEight.append(num).append(" ");
                    } else {
                        rightEight.append(num).append(" ");
                    }
                }

                String hex = Integer.toHexString(ROW_LEN * row++);
                String rowNum = String.format("%8s", hex).replace(" ", "0").toUpperCase();

                int width = ROW_LEN + ROW_LEN / 2 - 1;
                String firstHex = padRightWithSpace(leftEight.toString().trim().toUpperCase(), width);
                String secondHex = padRightWithSpace(rightEight.toString().trim().toUpperCase(), width);
                String out = String.format("%8s: %s|%s | %s", rowNum, firstHex, secondHex, converted.toString());

                // Reset StringBuilders
                leftEight.setLength(0);
                rightEight.setLength(0);
                converted.setLength(0);

                env.writeln(out);
            }
        } catch (FileNotFoundException e) {
            env.writeln("Could not find file.");
            return ShellStatus.CONTINUE;
        } catch (IOException e) {
            env.writeln("Could not read file.");
            return ShellStatus.CONTINUE;
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
