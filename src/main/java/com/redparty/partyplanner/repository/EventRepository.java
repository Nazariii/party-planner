package com.redparty.partyplanner.repository;


import com.redparty.partyplanner.common.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends BaseRepository<Event, Long>, JpaSpecificationExecutor<Event> {

}
