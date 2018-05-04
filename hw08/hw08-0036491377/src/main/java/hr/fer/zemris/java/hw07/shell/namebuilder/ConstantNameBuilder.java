package hr.fer.zemris.java.hw07.shell.namebuilder;

public class ConstantNameBuilder implements NameBuilder {
    private String constant;

    public ConstantNameBuilder(String constant) {
        this.constant = constant;
    }

    @Override
    public void execute(NameBuilderInfo info) {
        info.getStringBuilder().append(constant);
    }
}
