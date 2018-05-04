package hr.fer.zemris.java.hw07.shell.commands;

public enum Commands {
    CD("cd"),
    CAT("cat"),
    CHARSETS("charsets"),
    COPY("copy"),
    CPTREE("cptree"),
    DROPD("dropd"),
    EXIT("exit"),
    HELP("help"),
    HEXDUMP("hexdump"),
    LISTD("listd"),
    LS("ls"),
    MASSRENAME("massrename"),
    MKDIR("mkdir"),
    POPD("popd"),
    PUSHD("pushd"),
    PWD("pwd"),
    RMTREE("rmtree"),
    SYMBOL("symbol"),
    TREE("tree");

    private String name;
    Commands(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
