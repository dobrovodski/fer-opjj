package hr.fer.zemris.java.hw07.shell.namebuilder;

/**
 * Interface for NameBuilderInfo used to store and provide information about the pattern used to match files in current
 * directory and to hold the string builder.
 *
 * @author matej
 */
public interface NameBuilderInfo {
    /**
     * Returns the string builder object stored in the info.
     *
     * @return {@link StringBuilder} object stored in the info
     */
    StringBuilder getStringBuilder();

    /**
     * Returns string at index of matched pattern.
     *
     * @param index index of capturing group
     *
     * @return string that was captured
     */
    String getGroup(int index);

    /**
     * Returns the number of total captured groups.
     *
     * @return number of total captured groups
     */
    int getGroupCount();
}
