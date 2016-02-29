package com.redparty.partyplanner.common.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "EVENT")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class Event extends BaseEntity {

    @NotNull
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "event_status")
    private EventStatus eventStatus;

    @ManyToOne
    @JoinColumn(name="user_id",nullable = false)
    @JsonManagedReference
    private User user;

    @OneToMany(mappedBy = "pk.event",fetch = FetchType.LAZY)
    @Setter(AccessLevel.NONE)
    private Set<EventUnit> eventUnit = new HashSet<>();

    public enum EventStatus {
        HIDDEN, SHARED, CLOSED
    }

    public Event(String name, EventStatus eventStatus, User user) {
        this.name = name;
        this.eventStatus = eventStatus;
        this.user = user;
    }

    public void setEventStatusHidden(){
        this.eventStatus = EventStatus.HIDDEN;
    }

    public void setEventStatusShared(){
        this.eventStatus = EventStatus.SHARED;
    }

    public void setEventStatusClosed(){
        this.eventStatus = EventStatus.CLOSED;
    }

}
