package hr.fer.zemris.java.hw07.shell.namebuilder;

/**
 * Simple interface which provides a single generic method that is called when a NameBuilder needs to execute.
 *
 * @author matej
 */
public interface NameBuilder {
    /**
     * This method executes the NameBuilder's job.
     *
     * @param info builder information object
     */
    void execute(NameBuilderInfo info);
}
