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
