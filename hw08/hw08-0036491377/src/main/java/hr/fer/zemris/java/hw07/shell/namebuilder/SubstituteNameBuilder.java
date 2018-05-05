package hr.fer.zemris.java.hw07.shell.namebuilder;

/**
 * This builder builds the non-constant part of substitution expressions.
 *
 * @author matej
 */
public class SubstituteNameBuilder implements NameBuilder {
    /**
     * Substitution part of the expression.
     */
    private String substitution;

    /**
     * Constructor.
     *
     * @param substitution substitution part of the expression
     */
    public SubstituteNameBuilder(String substitution) {
        this.substitution = substitution;
    }

    /**
     * {@inheritDoc} Creates and appends a string to the info object. Substitution parts in the form of {number} insert
     * the n-th capturing group at that location whereas the form {number, 0number} and {number, number} left-pad the
     * capturing group with either zeroes or spaces.
     *
     * @param info builder information object
     */
    @Override
    public void execute(NameBuilderInfo info) {
        String[] split = substitution.split(",");
        if (split.length != 2 && split.length != 1) {
            throw new IllegalArgumentException("Substitution has too many separators: " + substitution);
        }

        int groupNum = Integer.parseInt(split[0]);

        if (groupNum > info.getGroupCount()) {
            throw new IllegalArgumentException("There are only " + info.getGroupCount() + " groups.");
        }

        StringBuilder group = new StringBuilder(info.getGroup(groupNum));

        if (split.length == 2) {
            String additionalBehaviour = split[1];
            if (additionalBehaviour.startsWith("0")) {
                additionalBehaviour = additionalBehaviour.substring(1);
                int n = Integer.parseInt(additionalBehaviour);
                while (group.length() != n) {
                    group.insert(0, "0");
                }
            } else {
                int n = Integer.parseInt(additionalBehaviour);
                while (group.length() != n) {
                    group.insert(0, " ");
                }
            }
        }

        info.getStringBuilder().append(group);
    }
}
