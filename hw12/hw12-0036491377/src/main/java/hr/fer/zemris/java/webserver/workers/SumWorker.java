package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

public class SumWorker implements IWebWorker {
    @Override
    public void processRequest(RequestContext context) {
        int a;
        int b;
        try {
            a = Integer.parseInt(context.getParameter("a"));
        } catch (NullPointerException | NumberFormatException e) {
            a = 1;
        }
        try {
            b = Integer.parseInt(context.getParameter("b"));
        } catch (NullPointerException | NumberFormatException e) {
            b = 2;
        }
        String sum = String.valueOf(a + b);
        context.setMimeType("text/html");
        context.setTemporaryParameter("zbroj", sum);
        context.setTemporaryParameter("a", String.valueOf(a));
        context.setTemporaryParameter("b", String.valueOf(b));
        try {
            context.getDispatcher().dispatchRequest("/private/calc.smscr");
        } catch (Exception ignored) {
        }
    }
}
