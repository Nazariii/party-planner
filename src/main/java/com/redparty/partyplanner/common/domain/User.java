package com.redparty.partyplanner.common.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

@Entity
@Table(name = "USER")
@Getter
@Setter
public class User extends BaseEntity{

    @Column(unique = true, nullable = false)
    @NotNull
    private String email;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    @Column(name = "auth_token", unique = true, nullable = false)
    private String authToken;

    @Column(name = "creation_date", nullable = false)
    private Date creationDate;

    @Column(nullable = false)
    private String name;
    private String phone;
    private String address;

    @OneToMany(mappedBy = "user")
    @JsonBackReference
    private Set<Event> events = new HashSet<>();

    @OneToMany(mappedBy = "user")
    @JsonBackReference
    private Set<Service> services = new HashSet<>();

    public String createAuthToken() {
        authToken = UUID.randomUUID().toString();
        return authToken;
    }

    User() {
        this.creationDate = new Date();
    }

    public User(String email, String password, String name, String phone) {
        this.creationDate = new Date();
        this.email = email;
        this.password = password;
        this.name = name;
        this.authToken = createAuthToken();
        this.phone = phone;
    }

    public User(String email, String password, String name, String phone, String address) {
        this(email, password, name, phone);
        this.address = address;
    }

    public String getEmail() {
        return email;
    }


    /*@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User that = (User) o;
        return Objects.equals(getEmail(), that.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEmail());
    }*/

    @Override
    public String toString() {
        return "User{" +
                "id=" + getId() +
                ", email='" + email + '\'' +
                ", authToken='" + authToken + '\'' +
                ", creationDate=" + creationDate +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
