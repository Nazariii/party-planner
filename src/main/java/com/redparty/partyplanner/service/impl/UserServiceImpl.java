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
        return null;
    }

    @Override
    public void add(User event) {

    }

    @Override
    public void add(String email, String password, String name) {

    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public User delete(Long id) {
        return null;
    }
}
