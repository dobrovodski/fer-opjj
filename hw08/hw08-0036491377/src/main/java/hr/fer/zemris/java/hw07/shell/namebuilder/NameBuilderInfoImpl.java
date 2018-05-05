package hr.fer.zemris.java.hw07.shell.namebuilder;

import java.util.regex.Matcher;

public class NameBuilderInfoImpl implements NameBuilderInfo {
    private StringBuilder sb;
    private Matcher matcher;

    public NameBuilderInfoImpl(Matcher matcher) {
        this.matcher = matcher;
        sb = new StringBuilder();
    }

    @Override
    public StringBuilder getStringBuilder() {
        return sb;
    }

    @Override
    public String getGroup(int index) {
        return matcher.group(index);
    }

    public Matcher getMatcher() {
        return matcher;
    }
}
