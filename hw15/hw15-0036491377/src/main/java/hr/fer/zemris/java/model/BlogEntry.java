package hr.fer.zemris.java.model;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Model for a single blog entry. It provides only getters and setters as well as hashcode and equals methods. It is
 * also an entry in the database in the table blogEntries.
 */
@NamedQueries({
        @NamedQuery(name = "BlogEntry.upit1", query = "select b from BlogComment as b where b.blogEntry=:be and b"
                                                      + ".postedOn>:when")
})
@Entity
@Table(name = "blog_entries")
@Cacheable(true)
public class BlogEntry {
    /**
     * Id.
     */
    private Long id;
    /**
     * List of comments on entry.
     */
    private List<BlogComment> comments = new ArrayList<>();
    /**
     * Timestamp of creation.
     */
    private Date createdAt;
    /**
     * Timestamp of modification.
     */
    private Date lastModifiedAt;
    /**
     * Title of entry.
     */
    private String title;
    /**
     * Content of entry.
     */
    private String text;
    /**
     * Creator of the entry.
     */
    private BlogUser creator;

    /**
     * Getter for id.
     *
     * @return id
     */
    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    /**
     * Setter for id.
     *
     * @param id id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Getter for comments.
     *
     * @return comments
     */
    @OneToMany(mappedBy = "blogEntry", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
    @OrderBy("postedOn")
    public List<BlogComment> getComments() {
        return comments;
    }

    /**
     * Setter for comments.
     *
     * @param comments comments
     */
    public void setComments(List<BlogComment> comments) {
        this.comments = comments;
    }

    /**
     * Getter for time of creation.
     *
     * @return time of creation
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * Setter for time of creation.
     *
     * @param createdAt time of creation
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Gets timestamp of last modification.
     *
     * @return timestamp of last modification
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = true)
    public Date getLastModifiedAt() {
        return lastModifiedAt;
    }

    /**
     * Setter for last modification
     *
     * @param lastModifiedAt last modification
     */
    public void setLastModifiedAt(Date lastModifiedAt) {
        this.lastModifiedAt = lastModifiedAt;
    }

    /**
     * Getter for the title of the entry.
     *
     * @return title of the entry
     */
    @Column(length = 200, nullable = false)
    public String getTitle() {
        return title;
    }

    /**
     * Setter for the title of the entry.
     *
     * @param title title of the entry
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Getter for the contents of the blog entry.
     *
     * @return content of the blog entry
     */
    @Column(length = 4096, nullable = false)
    public String getText() {
        return text;
    }

    /**
     * Setter for the contents of the blog entry.
     *
     * @param text content of the blog entry
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Getter for the creator of the blog entry.
     *
     * @return creator of the blog entry
     */
    @ManyToOne
    @JoinColumn(nullable = false)
    public BlogUser getCreator() {
        return creator;
    }

    /**
     * Setter for the creator of the blog entry.
     *
     * @param creator creator of the blog entry
     */
    public void setCreator(BlogUser creator) {
        this.creator = creator;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BlogEntry other = (BlogEntry) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}