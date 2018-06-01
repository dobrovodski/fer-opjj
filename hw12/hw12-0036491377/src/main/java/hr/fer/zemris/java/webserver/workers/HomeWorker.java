package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

public class HomeWorker implements IWebWorker {
    @Override
    public void processRequest(RequestContext context) throws Exception {
        String bgcolorValue = context.getPersistentParameter("bgcolor");
        if (bgcolorValue != null) {
            context.setTemporaryParameter("background", bgcolorValue);
        } else {
            context.setTemporaryParameter("background", "7F7F7F");
        }
        try {
            context.getDispatcher().dispatchRequest("/private/home.smscr");
        } catch (Exception ignored) {
        }
    }
}
