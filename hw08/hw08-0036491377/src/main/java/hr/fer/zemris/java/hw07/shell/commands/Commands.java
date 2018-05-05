package hr.fer.zemris.java.hw07.shell.commands;

/**
 * This is an enum used to store the "shell name" of each command independently of the actual code usage.
 *
 * @author matej
 */
public enum Commands {
    /**
     * cd command
     */
    CD("cd"),
    /**
     * cat command
     */
    CAT("cat"),
    /**
     * charsets command
     */
    CHARSETS("charsets"),
    /**
     * copy command
     */
    COPY("copy"),
    /**
     * cptree command
     */
    CPTREE("cptree"),
    /**
     * dropd command
     */
    DROPD("dropd"),
    /**
     * exit command
     */
    EXIT("exit"),
    /**
     * help command
     */
    HELP("help"),
    /**
     * hexdump command
     */
    HEXDUMP("hexdump"),
    /**
     * listd command
     */
    LISTD("listd"),
    /**
     * ls command
     */
    LS("ls"),
    /**
     * massrename command
     */
    MASSRENAME("massrename"),
    /**
     * mkdir command
     */
    MKDIR("mkdir"),
    /**
     * popd command
     */
    POPD("popd"),
    /**
     * pushd command
     */
    PUSHD("pushd"),
    /**
     * pwd command
     */
    PWD("pwd"),
    /**
     * rmtree command
     */
    RMTREE("rmtree"),
    /**
     * symbol command
     */
    SYMBOL("symbol"),
    /**
     * tree command
     */
    TREE("tree");

    private String name;

    Commands(String name) {
        this.name = name;
    }

    /**
     * Returns the name of the enum-represented command.
     *
     * @return name of the command
     */
    public String getName() {
        return name;
    }
}
