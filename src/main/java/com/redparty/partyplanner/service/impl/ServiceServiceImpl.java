package com.redparty.partyplanner.service.impl;

import com.redparty.partyplanner.common.domain.Service;
import com.redparty.partyplanner.common.domain.User;
import com.redparty.partyplanner.repository.ServiceRepository;
import com.redparty.partyplanner.repository.UserRepository;
import com.redparty.partyplanner.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.LocaleEditor;

import java.util.List;

@org.springframework.stereotype.Service
public class ServiceServiceImpl implements ServiceService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @Override
    public Service findServiceById(Long id) {
        return serviceRepository.findOne(id);
    }

    @Override
    public Service add(Service service) {
        return serviceRepository.save(service);
    }

    @Override
    public Service add(String name, Long userId) {
        User user = userRepository.findOne(userId);
        if (user == null) {
            return null;
        }
        return add(new Service(name, user));
    }

    @Override
    public List<Service> findAll() {
        return serviceRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        serviceRepository.delete(id);
    }
}
