package com.redparty.partyplanner.service.impl;

import com.redparty.partyplanner.common.domain.User;
import com.redparty.partyplanner.repository.UserRepository;
import com.redparty.partyplanner.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findUserById(Long id) {
        return userRepository.findOne(id);
    }

    @Override
    public User add(User event) {
        return userRepository.save(event);
    }

    @Override
    public User add(String email, String password, String name) {
        return add(new User(email, password, name));
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        userRepository.delete(id);
    }
}
