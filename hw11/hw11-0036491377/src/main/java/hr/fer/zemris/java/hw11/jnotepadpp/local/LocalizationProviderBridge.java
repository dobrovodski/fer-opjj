package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * Decorator for some other {@link ILocalizationProvider} which provides two additional methods: connect and disconnect.
 * It also manages a connection status (does not allow multiple connections).
 *
 * @author matej
 */
public class LocalizationProviderBridge extends AbstractLocalizationProvider {
    /**
     * Connected flag.
     */
    private boolean connected;
    /**
     * Localization provider.
     */
    private LocalizationProvider provider;
    /**
     * Listener
     */
    private ILocalizationListener listener;

    /**
     * Constructor.
     *
     * @param provider provider
     */
    public LocalizationProviderBridge(LocalizationProvider provider) {
        this.provider = provider;
        listener = this::fire;
        connected = false;
    }

    /**
     * Connects if possible and adds a localization listener to the given provider and then notifies the listener of the
     * change.
     */
    public void connect() {
        if (connected) {
            return;
        }

        connected = true;
        provider.addLocalizationListener(listener);
        listener.localizationChanged();
    }

    /**
     * Disconnects and removes the listener from the provider.
     */
    public void disconnect() {
        if (!connected) {
            return;
        }

        connected = false;
        provider.removeLocalizationListener(listener);
    }

    @Override
    public String getString(String key) {
        return provider.getString(key);
    }
}
