package hr.fer.zemris.java.hw07.shell.namebuilder;

public class SubstituteNameBuilder implements NameBuilder{
    private String substitution;

    public SubstituteNameBuilder(String substitution) {
        this.substitution = substitution;
    }
    @Override
    public void execute(NameBuilderInfo info) {
        //info.getStringBuilder().append(info.getGroup())
    }
}
