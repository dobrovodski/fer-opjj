package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.Locale;
import java.util.ResourceBundle;

public class LocalizationProvider extends AbstractLocalizationProvider {
    private static LocalizationProvider instance = new LocalizationProvider();
    private String language;
    private ResourceBundle bundle;

    private LocalizationProvider() {
        setLanguage("en");
    }

    public static LocalizationProvider getInstance() {
        return instance;
    }

    public void setLanguage(String language) {
        Locale locale = Locale.forLanguageTag(language);
        bundle = ResourceBundle.getBundle("hr.fer.zemris.java.hw11.jnotepadpp.local.prijevodi", locale);
        this.language = language;

        fire();
    }

    @Override
    public String getString(String key) {
        return bundle.getString(key);
    }
}
