package com.redparty.partyplanner.common.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "EVENT")
public class Event {

    protected Event() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "event_status")
    private EventStatus eventStatus;

    @ManyToOne
    @JoinColumn(name="user_id",nullable = false)
    private UserEntity user;

    @OneToMany(mappedBy = "pk.event",fetch = FetchType.LAZY)
    private Set<EventUnitEntity> eventUnit = new HashSet<>();

    public enum EventStatus {
        HIDDEN, SHARED, CLOSED
    }

    public Event(String name, EventStatus eventStatus, UserEntity user) {
        this.name = name;
        this.eventStatus = eventStatus;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    private void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EventStatus getEventStatus() {
        return eventStatus;
    }

    public void setEventStatus(EventStatus eventStatus) {
        this.eventStatus = eventStatus;
    }

    public void eventStatusHidden(){
        this.eventStatus = EventStatus.HIDDEN;
    }

    public void eventStatusShared(){
        this.eventStatus = EventStatus.SHARED;
    }

    public void eventStatusClosed(){
        this.eventStatus = EventStatus.CLOSED;
    }


    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(id, event.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
