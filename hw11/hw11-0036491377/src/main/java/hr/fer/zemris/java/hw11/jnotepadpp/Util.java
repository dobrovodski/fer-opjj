package hr.fer.zemris.java.hw11.jnotepadpp;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;

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
}
