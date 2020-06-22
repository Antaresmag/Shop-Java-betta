
package com.okunderenko.app.models;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "passhash")
    private String passhash;

    public User() {

    }

    public User(String firstName, String lastName, String email, String passhash) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.passhash = passhash;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasshash() {
        return passhash;
    }

    public void setPasshash(String passhash) {
        this.passhash = passhash;
    }

    @Override
    public String toString() {
        return "User [id=" + getId()
            + ", firstName=" + getFirstName()
            + ", lastName=" + getLastName()
            + ", email=" + getEmail()
            + ", passhash=" + getPasshash();
    }
}
