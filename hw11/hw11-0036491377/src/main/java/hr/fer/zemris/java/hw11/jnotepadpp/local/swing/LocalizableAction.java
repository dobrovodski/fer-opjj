package hr.fer.zemris.java.hw11.jnotepadpp.local.swing;

import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

import javax.swing.*;

/**
 * Localized version of {@link AbstractAction}.
 *
 * @author matej
 */
public abstract class LocalizableAction extends AbstractAction {
    /**
	 * Default serial version UID
	 */
	private static final long serialVersionUID = 1L;
    /**
     * Constructor for the localized AbstractAction.
     *
     * @param key translation key
     * @param lp localization provider
     */
    public LocalizableAction(String key, ILocalizationProvider lp) {
        String translation = lp.getString(key);
        putValue(Action.NAME, translation);

        lp.addLocalizationListener(() -> {
            String translatedName = lp.getString(key);
            // if we wanted to, we could make it so that the short description isn't always equal to the name
            // but for the purposes of this editor, it will be enough
            String translatedDescription = lp.getString(key);
            putValue(Action.NAME, translatedName);
            putValue(Action.SHORT_DESCRIPTION, translatedDescription);
        });
    }
}
