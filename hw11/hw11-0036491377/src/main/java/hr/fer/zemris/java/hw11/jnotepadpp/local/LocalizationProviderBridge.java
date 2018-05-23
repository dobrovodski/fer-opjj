package hr.fer.zemris.java.hw11.jnotepadpp.local;

public class LocalizationProviderBridge extends AbstractLocalizationProvider {
    private boolean connected;
    private LocalizationProvider provider;
    private ILocalizationListener listener;

    public LocalizationProviderBridge(LocalizationProvider provider) {
        this.provider = provider;
        listener = this::fire;
        connected = false;
    }

    public void connect() {
        if (connected) {
            return;
        }

        connected = true;
        provider.addLocalizationListener(listener);
        listener.localizationChanged();
    }

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
