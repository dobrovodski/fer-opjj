package hr.fer.zemris.java.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * This class models a single blog comment. It provides only getters and setters as well as hashcode and equals methods.
 * It is also an entry in the database in the table blogComments.
 *
 * @author matej
 */
@Entity
@Table(name = "blog_comments")
public class BlogComment {
    /**
     * Id of comment.
     */
    private Long id;
    /**
     * Blog entry to which the comment belongs to.
     */
    private BlogEntry blogEntry;
    /**
     * Email of the user who posted this comment.
     */
    private String usersEMail;
    /**
     * Content of the comment.
     */
    private String message;
    /**
     * Date when the comment was posted.
     */
    private Date postedOn;

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
     * Getter for blog entry.
     *
     * @return blog entry
     */
    @ManyToOne
    @JoinColumn(nullable = false)
    public BlogEntry getBlogEntry() {
        return blogEntry;
    }

    /**
     * Setter for blog entry.
     *
     * @param blogEntry blog entry
     */
    public void setBlogEntry(BlogEntry blogEntry) {
        this.blogEntry = blogEntry;
    }

    /**
     * Getter for user email.
     *
     * @return user email
     */
    @Column(length = 100, nullable = false)
    public String getUsersEMail() {
        return usersEMail;
    }

    /**
     * Setter for users email.
     *
     * @param usersEMail users email
     */
    public void setUsersEMail(String usersEMail) {
        this.usersEMail = usersEMail;
    }

    /**
     * Getter for content of the comment.
     *
     * @return content of the comment
     */
    @Column(length = 4096, nullable = false)
    public String getMessage() {
        return message;
    }

    /**
     * Setter for content of the comment.
     *
     * @param message content of the comment
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Getter for timestamp of time when it was posted.
     *
     * @return timestamp
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    public Date getPostedOn() {
        return postedOn;
    }

    /**
     * Setter for timestamp of time when it was posted
     *
     * @param postedOn timestamp of time when it was posted
     */
    public void setPostedOn(Date postedOn) {
        this.postedOn = postedOn;
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
        BlogComment other = (BlogComment) obj;
        if (id == null) {
            return other.id == null;
        } else
            return id.equals(other.id);
    }
}