package com.redparty.partyplanner.common.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "USER")
@Getter
@Setter
public class User extends BaseEntity {

    @Column(unique = true, nullable = false)
    @NotNull
    private String email;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

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


    User() {
        this.creationDate = new Date();
    }

    public User(String email, String password, String name, String phone) {
        this.creationDate = new Date();
        this.email = email;
        this.password = password;
        this.name = name;
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
                ", creationDate=" + creationDate +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
