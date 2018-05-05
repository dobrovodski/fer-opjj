package hr.fer.zemris.java.hw07.shell.namebuilder;

/**
 * Builder which builds constant parts of a substitution expression.
 *
 * @author matej
 */
public class ConstantNameBuilder implements NameBuilder {
    private String constant;

    /**
     * Constructor.
     *
     * @param constant string which represents a constant part of the substitution expression
     */
    public ConstantNameBuilder(String constant) {
        this.constant = constant;
    }

    /**
     * {@inheritDoc} This method appends the constant string to the provided info object.
     *
     * @param info builder information object
     */
    @Override
    public void execute(NameBuilderInfo info) {
        info.getStringBuilder().append(constant);
    }
}
