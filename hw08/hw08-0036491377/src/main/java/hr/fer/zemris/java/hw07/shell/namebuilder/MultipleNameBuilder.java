package hr.fer.zemris.java.hw07.shell.namebuilder;

import java.util.List;

public class MultipleNameBuilder implements NameBuilder {
    private List<NameBuilder> builders;

    public MultipleNameBuilder(List<NameBuilder> builders) {
        this.builders = builders;
    }

    @Override
    public void execute(NameBuilderInfo info) {
        for (NameBuilder b : builders) {
            b.execute(info);
        }
    }
}
