package com.redparty.partyplanner.common.domain;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class EventUnitId implements Serializable {

    @ManyToOne
    private EventEntity event;

    @ManyToOne
    private ServiceEntity service;

    public EventEntity getEvent() {
        return event;
    }

    public void setEvent(EventEntity event) {
        this.event = event;
    }

    public ServiceEntity getService() {
        return service;
    }

    public void setService(ServiceEntity service) {
        this.service = service;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventUnitId that = (EventUnitId) o;
        return Objects.equals(event, that.event) &&
                Objects.equals(service, that.service);
    }

    @Override
    public int hashCode() {
        return Objects.hash(event, service);
    }
}
