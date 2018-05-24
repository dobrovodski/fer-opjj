package hr.fer.zemris.java.hw11.jnotepadpp.local.swing;

import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

import javax.swing.*;

/**
 * Localized {@link JLabel}.
 *
 * @author matej
 */
public class LJLabel extends JLabel {
    /**
	 * Default serial version UID
	 */
	private static final long serialVersionUID = 1L;
    /**
     * Constructor for the localized JLabel.
     *
     * @param key translation key
     * @param lp localization provider
     */
    public LJLabel(String key, ILocalizationProvider lp) {
        String translation = lp.getString(key);
        setText(translation);

        lp.addLocalizationListener(() -> {
            String translated = lp.getString(key);
            setText(translated);
        });
    }
}
