package hr.fer.zemris.java.hw07.shell.namebuilder;

import java.util.ArrayList;
import java.util.List;

public class NameBuilderParser {
    String expression;

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
        StringBuilder sb = new StringBuilder();
        StringBuilder substitutionSb = new StringBuilder();

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            if (c != '{' && c != '$' && !inSubstitution) {
                sb.append(c);
                continue;
            }

            if (c == '$' && !inSubstitution) {
                if (i < expression.length() - 1 && expression.charAt(i + 1) == '{') {
                    inSubstitution = true;
                    i++;
                    builders.add(new ConstantNameBuilder(sb.toString()));
                    sb.setLength(0);
                    continue;
                }
            }

            if (inSubstitution) {
                if (Character.isWhitespace(c)) {
                    while (true) {
                        if (c == ',') {
                            sb.append(c);
                            break;
                        }
                        if (Character.isDigit(c)) {
                            throw new IllegalArgumentException("Spaces not allowed between digits.");
                        }
                        if (i == expression.length() - 1) {
                            throw new IllegalArgumentException("Substitution not closed.");
                        }
                        c = expression.charAt(++i);
                    }
                }

                if (c == '}') {
                    inSubstitution = false;
                    builders.add(new SubstituteNameBuilder(substitutionSb.toString()));
                    substitutionSb.setLength(0);
                    continue;
                }

                if (Character.isDigit(c) || c == ',') {
                    substitutionSb.append(c);
                    continue;
                }

                throw new IllegalArgumentException("Illegal substitution symbol: " + c);
            }
        }

        if (sb.length() > 0) {
            builders.add(new ConstantNameBuilder(sb.toString()));
        }

        return builders;
    }
}
