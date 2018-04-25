package hr.fer.zemris.java.hw07.shell;

import java.util.ArrayList;
import java.util.List;

public class Util {
    public static List<String> split(String arguments) {
        List<String> matches = new ArrayList<>();

        boolean inQuotes = false;
        StringBuilder sb = new StringBuilder();

        for (int i = 0, len = arguments.length(); i < len; i++) {
            char c = arguments.charAt(i);

            //  If you've reached the end, append whatever you've got (special case is ending a quote)
            if (i == len - 1) {
                if (c != '"' || !inQuotes) {
                    sb.append(c);
                }
                if (sb.length() > 0) {
                    matches.add(sb.toString());
                    sb.setLength(0);
                }
                continue;
            }

            // Don't append the character '\' if you're escaping a quote or if its \\
            if (c == '\\' && i < len - 1) {
                if(arguments.charAt(i + 1) == '"' && inQuotes) {
                    continue;
                }
                if (arguments.charAt(i + 1) == '\\') {
                    continue;
                }
            }

            // If entering a quote, just continue. If exiting a quote, add whatever you've got to the list
            if (c == '"') {
                if (i == 0) {
                    inQuotes = true;
                    continue;
                } else if (arguments.charAt(i - 1) != '\\') {
                    inQuotes = !inQuotes;
                    // 'After the ending double-quote, either no more characters must be present or at least one
                    // space character must be present'
                    if (i < len && !inQuotes && !Character.isWhitespace(arguments.charAt(i + 1))) {
                        return null;
                    }

                    if (sb.length() > 0) {
                        matches.add(sb.toString());
                        sb.setLength(0);
                    }
                    continue;
                }
            }

            // Whitespace gets appended if it's in quotes, otherwise it's a boundary between arguments
            if (Character.isWhitespace(c)) {
                if (inQuotes) {
                    sb.append(c);
                    continue;
                } else {
                    // To prevent splitting on empty strings
                    if (sb.length() > 0) {
                        matches.add(sb.toString());
                        sb.setLength(0);
                    }
                    continue;
                }
            }

            // Everything else simply get appended
            sb.append(c);
        }

        return matches;
    }
}
