package com.redparty.partyplanner.common.domain;

import javax.persistence.*;

@Entity
@Table(name = "EVENT_UNIT")
@AssociationOverrides({
        @AssociationOverride(name = "pk.event",
                joinColumns = @JoinColumn(name = "event_id")),
        @AssociationOverride(name = "pk.service",
                joinColumns = @JoinColumn(name = "service_id"))})
public class EventUnit {

    protected EventUnit(){}

    @EmbeddedId
    private EventUnitId pk = new EventUnitId();

    @Enumerated(EnumType.STRING)
    @Column(name = "service_status")
    private ServiceStatus serviceStatus;

    public enum ServiceStatus {
        PENDING, REJECTED, ACCEPTED
    }

    @Transient
    public Event getEvent() {
        return pk.getEvent();
    }

    public void setEvent(Event event) {
        pk.setEvent(event);
    }

    @Transient
    public Service getService() {
        return pk.getService();
    }

    public void setService(Service service) {
        pk.setService(service);
    }

    public ServiceStatus getServiceStatus() {
        return serviceStatus;
    }

    public void setServiceStatus(ServiceStatus serviceStatus) {
        this.serviceStatus = serviceStatus;
    }

    /*@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventUnit that = (EventUnit) o;
        return Objects.equals(pk, that.pk);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pk);
    }*/
}
