package hr.fer.zemris.java.custom.scripting.demo;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This is a demo program for the {@link SmartScriptEngine}. It reads a pre-set fibonaccih.smscr file and executes the
 * engine. The expected output is an html file with fibonacci numbers in a table written to the standard output.
 *
 * @author matej
 */
public class SmartScriptEngineDemo {
    /**
     * Entry point.
     *
     * @param args not used
     */
    public static void main(String[] args) {
        String path = "webroot/scripts/fibonaccih.smscr";
        String documentBody;
        try {
            documentBody = new String(Files.readAllBytes(Paths.get(path)));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        Map<String, String> parameters = new HashMap<>();
        Map<String, String> persistentParameters = new HashMap<>();
        List<RequestContext.RCCookie> cookies = new ArrayList<>();

        new SmartScriptEngine(
                new SmartScriptParser(documentBody).getDocumentNode(),
                new RequestContext(System.out, parameters, persistentParameters, cookies)
        ).execute();
    }
}
