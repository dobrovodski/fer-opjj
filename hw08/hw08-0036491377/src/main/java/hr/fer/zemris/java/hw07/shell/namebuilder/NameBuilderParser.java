package hr.fer.zemris.java.hw07.shell.namebuilder;

import java.util.ArrayList;
import java.util.List;

public class NameBuilderParser {
    String expression;

    public NameBuilderParser(String expression) {
        this.expression = expression;
    }

    public NameBuilder getNameBuilder() {
        List<NameBuilder> builders = new ArrayList<>();



        return new MultipleNameBuilder(builders);
    }
}
