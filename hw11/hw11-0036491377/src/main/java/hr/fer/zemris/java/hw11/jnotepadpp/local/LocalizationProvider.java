package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.text.Collator;
import java.util.Locale;
import java.util.ResourceBundle;

public class LocalizationProvider extends AbstractLocalizationProvider {
    private static LocalizationProvider instance = new LocalizationProvider();
    private String language;
    private static Locale locale;
    private ResourceBundle bundle;

    private LocalizationProvider() {
        setLanguage("en");
    }

    public static LocalizationProvider getInstance() {
        return instance;
    }

    public static Collator getCollator() {
        return Collator.getInstance(locale);
    }

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
