package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * Provides translation for given keys and methods for registering and removing listeners.
 *
 * @author matej
 */
public interface ILocalizationProvider {
    /**
     * Adds a listener.
     * @param listener listener to add
     */
    void addLocalizationListener(ILocalizationListener listener);

    /**
     * Removes a listener.
     * @param listener listener to remove
     */
    void removeLocalizationListener(ILocalizationListener listener);

    /**
     * Returns translated string under given key in the relevant properties file.
     * @param key key for translation
     * @return translated string for given key
     */
    String getString(String key);
}
