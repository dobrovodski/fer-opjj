package hr.fer.zemris.java.hw11.jnotepadpp;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class Util {
    public static ImageIcon loadIcon(String path) {
        InputStream is = Util.class.getResourceAsStream(path);
        byte[] bytes;

        try {
            bytes = is.readAllBytes();
        } catch (IOException e) {
            return null;
        }

        try {
            is.close();
        } catch (IOException e) {
            return null;
        }

        return new ImageIcon(bytes);
    }

    public static String readFile(Path path, Component comp) {
        byte[] bytes;
        try {
            bytes = Files.readAllBytes(path);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(comp,
                    "Could not read file: " + path.getFileName().toAbsolutePath(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return null;
        }
        return new String(bytes, StandardCharsets.UTF_8);
    }
}
