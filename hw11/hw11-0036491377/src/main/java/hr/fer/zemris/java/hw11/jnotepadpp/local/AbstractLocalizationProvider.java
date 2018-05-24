package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.ArrayList;
import java.util.List;

/**
 * Implements ILocalizationProvider interface and adds it the ability to register, de-register and inform (fire()
 * method) listeners.
 *
 * @author matej
 */
public abstract class AbstractLocalizationProvider implements ILocalizationProvider {
    /**
     * Listeners.
     */
    private List<ILocalizationListener> listeners = new ArrayList<>();

    @Override
    public void addLocalizationListener(ILocalizationListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeLocalizationListener(ILocalizationListener listener) {
        listeners.remove(listener);
    }

    /**
     * Notifies listeners.
     */
    public void fire() {
        for (ILocalizationListener l : listeners) {
            l.localizationChanged();
        }
    }
}
