package hr.fer.zemris.java.hw07.shell.namebuilder;

import java.util.ArrayList;
import java.util.List;

public class NameBuilderParser {
    private String expression;

    public NameBuilderParser(String expression) {
        this.expression = expression;
    }

    public MultipleNameBuilder getNameBuilder() {
        List<NameBuilder> builders = parse(expression);
        return new MultipleNameBuilder(builders);
    }

    private List<NameBuilder> parse(String expression) {
        List<NameBuilder> builders = new ArrayList<>();

        boolean inSubstitution = false;
        boolean inNumber = false;
        StringBuilder sb = new StringBuilder();
        StringBuilder substitutionSb = new StringBuilder();

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            // Append any regular characters if not in substitution mode
            if (c != '$' && !inSubstitution) {
                sb.append(c);
                continue;
            }

            // If you bump into $, check if the next one is {
            // if so, enter substitution mode, create a constant name builder from whatever you've got so far
            // skip the {
            // Otherwise, if the next one isn't {, just append the $
            if (c == '$' && !inSubstitution) {
                if (i < expression.length() - 1 && expression.charAt(i + 1) == '{') {
                    inSubstitution = true;
                    i++;
                    if (sb.length() > 0) {
                        builders.add(new ConstantNameBuilder(sb.toString()));
                    }
                    sb.setLength(0);
                    continue;
                } else {
                    sb.append(c);
                    continue;
                }
            }

            // This part below is for substitution mode

            // Skip whitespaces (only add commas, that's the only symbol that can appear)
            // Exceptions are non-closed substitutions and spaces between digits
            while (Character.isWhitespace(c)) {
                if (c == ',') {
                    sb.append(c);
                    break;
                }
                if (i == expression.length() - 1) {
                    throw new IllegalArgumentException("Substitution not closed.");
                }
                c = expression.charAt(++i);
                if (Character.isDigit(c) && inNumber) {
                    throw new IllegalArgumentException("Spaces not allowed between digits.");
                }
            }

            // End substitution mode here
            if (c == '}') {
                inSubstitution = false;
                inNumber = false;
                if (substitutionSb.length() > 0) {
                    builders.add(new SubstituteNameBuilder(substitutionSb.toString()));
                }
                substitutionSb.setLength(0);
                continue;
            }

            // inNumber is a flag to check for spaces between numbers
            if (Character.isDigit(c)) {
                substitutionSb.append(c);
                inNumber = true;
                continue;
            }

            // if reached a comma, not in a number anymore
            if (c == ',') {
                substitutionSb.append(c);
                inNumber = false;
                continue;
            }

            // Everything else is illegal !
            throw new IllegalArgumentException("Illegal substitution symbol: " + c);
        }

        // Append anything left in the constant stringbuilder
        if (sb.length() > 0) {
            builders.add(new ConstantNameBuilder(sb.toString()));
        }

        return builders;
    }
}
