package com.redparty.partyplanner.service.impl;

import com.redparty.partyplanner.common.domain.Service;
import com.redparty.partyplanner.common.domain.User;
import com.redparty.partyplanner.common.exception.ResourceCRUDException;
import com.redparty.partyplanner.common.exception.ResourceNotFoundException;
import com.redparty.partyplanner.repository.ServiceRepository;
import com.redparty.partyplanner.repository.UserRepository;
import com.redparty.partyplanner.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@org.springframework.stereotype.Service
@Transactional
public class ServiceServiceImpl implements ServiceService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @Override
    public Service findServiceById(Long id) {
        return serviceRepository.findOneById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service", "id", id));
    }

    @Override
    public Service add(Service service) {
        return serviceRepository.save(service)
                .orElseThrow(() -> new ResourceCRUDException("Service", "Name", service.getName(), "Created"));
    }

    @Override
    public Service add(String name, Long userId) {
        User user = userRepository.findOneById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        return add(new Service(name, user));
    }

    @Override
    public List<Service> findAll() {
        return serviceRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        serviceRepository.deleteById(id);
    }
}
