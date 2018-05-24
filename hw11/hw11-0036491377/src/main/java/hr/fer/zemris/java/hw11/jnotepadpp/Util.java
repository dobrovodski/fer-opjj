package hr.fer.zemris.java.hw11.jnotepadpp;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Utility class which provides methods for loading icons and reading files into strings.
 *
 * @author matej
 */
public class Util {
    /**
     * Loads icon from the given path and returns it.
     *
     * @param path path to the icon
     *
     * @return ImageIcon
     */
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

    /**
     * Reads file into {@link String} and returns it. Displays error message on comp if something went wrong.
     *
     * @param path path from which to load the file
     * @param comp component which will get an error message if something goes wrong
     *
     * @return string with text or null if file couldn't be read
     */
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
