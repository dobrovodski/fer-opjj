package hr.fer.zemris.java.hw07.shell.namebuilder;

import java.util.List;

/**
 * This class aggregates multiple {@link NameBuilder}s and provides a single method to execute all of them one after
 * another in the order they were put in the list.
 *
 * @author matej
 */
public class MultipleNameBuilder implements NameBuilder {
    /**
     * Stores the builders.
     */
    private List<NameBuilder> builders;

    /**
     * Constructor.
     *
     * @param builders list of builders to store
     */
    public MultipleNameBuilder(List<NameBuilder> builders) {
        this.builders = builders;
    }

    /**
     * {@inheritDoc} This method sequentially executes all the builders stored in the class.
     *
     * @param info builder information object
     */
    @Override
    public void execute(NameBuilderInfo info) {
        for (NameBuilder b : builders) {
            b.execute(info);
        }
    }
}
