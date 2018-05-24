package hr.fer.zemris.java.hw11.jnotepadpp.local;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Extends {@link LocalizationProviderBridge} and registers itself to the window as a listener to keep track of when to
 * connect or disconnect.
 *
 * @author matej
 */
public class FormLocalizationProvider extends LocalizationProviderBridge {
    /**
     * Constructor.
     *
     * @param provider provider
     * @param frame frame to attach itself to
     */
    public FormLocalizationProvider(LocalizationProvider provider, JFrame frame) {
        super(provider);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                connect();
            }

            @Override
            public void windowClosed(WindowEvent e) {
                disconnect();
            }
        });
    }
}
