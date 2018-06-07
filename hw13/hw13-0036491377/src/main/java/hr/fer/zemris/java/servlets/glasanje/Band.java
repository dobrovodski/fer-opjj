package hr.fer.zemris.java.servlets.glasanje;

public class Band {
    private int id;
    private String name;
    private String link;
    private int voteCount;

    public Band(int id, String name, String link) {
        this.id = id;
        this.name = name;
        this.link = link;
        this.voteCount = 0;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLink() {
        return link;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }
}
