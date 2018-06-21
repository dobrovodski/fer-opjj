package hr.fer.zemris.java.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

/**
 * This class represents a single registered user in the database. It provides only getters and setters as well as
 * hashcode and equals methods. It is also an entry in the database in the table blogUsers.
 */
@Entity
@Table(name = "blog_users")
public class BlogUser {
    /**
     * Id.
     */
    private Long id;
    /**
     * First name of user.
     */

    private String firstName;
    /**
     * Last name of user.
     */
    private String lastName;
    /**
     * Username of user.
     */
    private String nick;
    /**
     * Email of user.
     */
    private String email;
    /**
     * Hashed user password.
     */
    private String passwordHash;
    /**
     * List of blog entries the user has made.
     */
    private List<BlogEntry> entries;

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
     * Getter for first name.
     *
     * @return first name
     */
    @Column(length = 200, nullable = false)
    public String getFirstName() {
        return firstName;
    }

    /**
     * Setter for first name.
     *
     * @param firstName first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Getter for last name.
     *
     * @return last name
     */
    @Column(length = 200, nullable = false)
    public String getLastName() {
        return lastName;
    }

    /**
     * Setter for last name.
     *
     * @param lastName last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Getter for username.
     *
     * @return username
     */
    @Column(length = 200, nullable = false, unique = true)
    public String getNick() {
        return nick;
    }

    /**
     * Setter for username.
     *
     * @param nick username
     */
    public void setNick(String nick) {
        this.nick = nick;
    }

    /**
     * Getter for email.
     *
     * @return email
     */
    @Column(length = 200, nullable = false)
    public String getEmail() {
        return email;
    }

    /**
     * Setter for email.
     *
     * @param email email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Getter for password hash.
     *
     * @return password hash
     */
    @Column(length = 40, nullable = false)
    public String getPasswordHash() {
        return passwordHash;
    }

    /**
     * Setter for password hash.
     *
     * @param passwordHash password hash
     */
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    /**
     * Getter for blog entry list.
     *
     * @return list of blog entries.
     */
    @OneToMany(mappedBy = "creator", fetch = FetchType.LAZY, cascade = CascadeType.MERGE, orphanRemoval = true)
    public List<BlogEntry> getEntries() {
        return entries;
    }

    /**
     * Setter for blog entry list
     *
     * @param entries blog entry list
     */
    public void setEntries(List<BlogEntry> entries) {
        this.entries = entries;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((nick == null) ? 0 : nick.hashCode());
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
        BlogUser other = (BlogUser) obj;
        if (nick == null) {
            if (other.nick != null)
                return false;
        } else if (!nick.equals(other.nick))
            return false;
        return true;
    }
}