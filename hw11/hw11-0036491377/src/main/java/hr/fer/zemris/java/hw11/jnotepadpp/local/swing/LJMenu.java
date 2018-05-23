package hr.fer.zemris.java.hw11.jnotepadpp.local.swing;

import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

import javax.swing.*;

public class LJMenu extends JMenu {
    public LJMenu(String key, ILocalizationProvider lp) {
        String translation = lp.getString(key);
        setText(translation);

        lp.addLocalizationListener(() -> {
            String translated = lp.getString(key);
            setText(translated);
        });
    }
}
