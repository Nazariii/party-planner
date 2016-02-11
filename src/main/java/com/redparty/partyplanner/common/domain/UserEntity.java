package com.redparty.partyplanner.common.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "USER")
public class UserEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true, nullable = false)
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
    private Set<EventEntity> events = new HashSet<>();

    public String createAuthToken() {
        authToken = UUID.randomUUID().toString();
        return authToken;
    }

    UserEntity() {
        this.creationDate = new Date();
    }

    public UserEntity(String email, String password, String name) {
        this.creationDate = new Date();
        this.email = email;
        this.password = password;
        this.name = name;
        this.authToken = createAuthToken();
    }

    public UserEntity(String email, String password, String name, String phone, String address) {
        this(email, password, name);
        this.phone = phone;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Set<EventEntity> getEvents() {
        return events;
    }

    public void setEvents(Set<EventEntity> events) {
        this.events = events;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return Objects.equals(getEmail(), that.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEmail());
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", authToken='" + authToken + '\'' +
                ", creationDate=" + creationDate +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
