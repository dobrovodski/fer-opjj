package hr.fer.zemris.java.servlets;

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

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }
}
