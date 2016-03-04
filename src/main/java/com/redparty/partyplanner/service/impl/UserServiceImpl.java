package com.redparty.partyplanner.service.impl;

import com.redparty.partyplanner.common.domain.User;
import com.redparty.partyplanner.common.exception.ResourceCRUDException;
import com.redparty.partyplanner.common.exception.ResourceNotFoundException;
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
        return userRepository.findOne(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
    }

    @Override
    public User add(User user) {
        return userRepository.save(user)
                .orElseThrow(() -> new ResourceCRUDException("User", "Email", user.getEmail(), "Created"));
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
