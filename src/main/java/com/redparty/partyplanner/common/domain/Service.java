package com.redparty.partyplanner.common.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "SERVICE")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
public class Service extends BaseEntity {

    private String name;

    public Service(String name, User user) {
        this.name = name;
        this.user = user;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "pk.service", fetch = FetchType.LAZY)
    private Set<EventUnit> eventUnit = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Service service = (Service) o;
        return Objects.equals(getId(), service.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
