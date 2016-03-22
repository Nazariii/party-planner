package com.redparty.partyplanner.service.impl;

import com.redparty.partyplanner.common.domain.User;
import com.redparty.partyplanner.common.exception.ResourceCRUDException;
import com.redparty.partyplanner.common.exception.ResourceNotFoundException;
import com.redparty.partyplanner.repository.UserRepository;
import com.redparty.partyplanner.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

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
    public User add(String email, String password, String name, String phone) {
        return add(new User(email, bCryptPasswordEncoder.encode(password), name, phone));
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        userRepository.delete(id);
    }

    @Override
    public boolean existsWithEmail(String email) {
        return userRepository.isPresent(email);
    }
}
