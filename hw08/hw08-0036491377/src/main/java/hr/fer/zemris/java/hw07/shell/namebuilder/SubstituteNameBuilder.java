package hr.fer.zemris.java.hw07.shell.namebuilder;

public class SubstituteNameBuilder implements NameBuilder {
    private String substitution;

    public SubstituteNameBuilder(String substitution) {
        this.substitution = substitution;
    }

    @Override
    public void execute(NameBuilderInfo info) {
        String[] split = substitution.split(",");
        if (split.length != 2 && split.length != 1) {
            throw new IllegalArgumentException("Substitution has too many commas: " + substitution);
        }

        int groupNum = Integer.parseInt(split[0]);

        if (groupNum > info.getMatcher().groupCount()) {
            throw new IllegalArgumentException("There are only " + info.getMatcher().groupCount() + " groups.");
        }

        String group = info.getMatcher().group(groupNum);

        if (split.length == 2) {
            String additionalBehaviour = split[1];
            if (additionalBehaviour.startsWith("0")) {
                additionalBehaviour = additionalBehaviour.substring(1);
                int n = Integer.parseInt(additionalBehaviour);
                while (group.length() != n) {
                    group = "0" + group;
                }
            } else {
                int n = Integer.parseInt(additionalBehaviour);
                while (group.length() != n) {
                    group = " " + group;
                }
            }
        }

        info.getStringBuilder().append(group);
    }
}
