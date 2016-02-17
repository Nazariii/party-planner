package com.redparty.partyplanner.service;

import com.redparty.partyplanner.common.domain.Event;
import com.redparty.partyplanner.common.domain.Service;

import java.util.List;

public interface ServiceService {

    Service findServiceById(Long id);

    Service add(Service service);

    Service add(String name, Long userId);

    List<Service> findAll();

    void delete(Long id);
}
