package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.text.Collator;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Singleton class used to set the current language and to get translations for the current language.
 *
 * @author matej
 */
public class LocalizationProvider extends AbstractLocalizationProvider {
    /**
     * Reference to single instance of itself.
     */
    private static LocalizationProvider instance = new LocalizationProvider();
    /**
     * Current language.
     */
    private String language;
    /**
     * Current locale.
     */
    private static Locale locale;
    /**
     * ResourceBundle used to get translations.
     */
    private ResourceBundle bundle;

    /**
     * Default constructor which sets the language to english.
     */
    private LocalizationProvider() {
        setLanguage("en");
    }

    /**
     * Returns reference to singleton object.
     * @return reference to singleton
     */
    public static LocalizationProvider getInstance() {
        return instance;
    }

    /**
     * Returns the Collator for the current locale.
     * @return Collator for current locale
     */
    public static Collator getCollator() {
        return Collator.getInstance(locale);
    }

    /**
     * Sets the language to the given one and notifies all listeners.
     * @param language language to switch to.
     */
    public void setLanguage(String language) {
        this.language = language;
        locale = Locale.forLanguageTag(language);
        bundle = ResourceBundle.getBundle("hr.fer.zemris.java.hw11.jnotepadpp.local.prijevodi", locale);
        fire();
    }

    @Override
    public String getString(String key) {
        return bundle.getString(key);
    }
}
