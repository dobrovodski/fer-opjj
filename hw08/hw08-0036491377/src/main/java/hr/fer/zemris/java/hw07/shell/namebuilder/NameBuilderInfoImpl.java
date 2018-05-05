package hr.fer.zemris.java.hw07.shell.namebuilder;

import java.util.regex.Matcher;

/**
 * Implementation of the {@link NameBuilderInfo} used to store and provide information about the pattern used to match
 * files in current directory and to hold the string builder.
 *
 * @author matej
 */
public class NameBuilderInfoImpl implements NameBuilderInfo {
    /**
     * StringBuilder used to generate the final output.
     */
    private StringBuilder sb;
    /**
     * Matcher used for regex-like pattern matching.
     */
    private Matcher matcher;

    /**
     * Constructor.
     *
     * @param matcher matcher to use for pattern matching
     */
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

    @Override
    public int getGroupCount() {
        return matcher.groupCount();
    }
}
