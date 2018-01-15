package com.redparty.partyplanner.repository;

import com.redparty.partyplanner.common.domain.Service;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRepository extends BaseRepository<Service, Long>, JpaSpecificationExecutor<Service> {
}
