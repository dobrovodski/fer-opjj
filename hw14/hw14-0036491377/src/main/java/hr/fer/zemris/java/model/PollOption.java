package hr.fer.zemris.java.model;

public class PollOption {
    private String title;
    private String link;
    private int id;
    private long pollId;
    private int voteCount;

    public PollOption() {
    }

    public PollOption(String title, String link, int voteCount) {
        this.title = title;
        this.link = link;
        this.voteCount = voteCount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getPollId() {
        return pollId;
    }

    public void setPollId(long pollId) {
        this.pollId = pollId;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }
}
