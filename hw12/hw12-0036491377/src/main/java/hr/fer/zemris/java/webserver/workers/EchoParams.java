package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

import java.io.IOException;
import java.util.Set;

public class EchoParams implements IWebWorker {
    @Override
    public void processRequest(RequestContext context) {
        context.setMimeType("text/html");
        try {
            context.write("<html><body>");
            context.write("<table>");
            Set<String> paramNames = context.getParameterNames();
            for (String paramName : paramNames) {
                context.write("<tr>");
                context.write("<td>" + paramName + "</td>");
                context.write("<td>" + context.getParameter(paramName) + "</td>");
                context.write("</tr>");
            }
            context.write("</table>");
            context.write("</body></html>");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
