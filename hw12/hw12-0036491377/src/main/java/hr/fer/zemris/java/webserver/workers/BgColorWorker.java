package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * {@link IWebWorker} used to notify about the update of index2.html's background color. It also provides a link to
 * return to index2.html.
 *
 * @author matej
 */
public class BgColorWorker implements IWebWorker {
    @Override
    public void processRequest(RequestContext context) {
        String bgcolorValue = context.getParameter("bgcolor");
        Pattern patter = Pattern.compile("[0-9A-Fa-f]{6}");
        Matcher m = patter.matcher(bgcolorValue);
        context.setMimeType("text/html");
        try {
            context.write("<html><body>");
            context.write("<a href=\"/index2.html\"<br> index2.html </a>");
            context.write("Color updated: ");
            if (m.matches()) {
                context.setPersistentParameter("bgcolor", bgcolorValue);
                context.write("<b>YES</b>");
            } else {
                context.write("<b>NO</b>");
            }
            context.write("</body></html>");
        } catch (IOException ignored) {
        }
    }
}
