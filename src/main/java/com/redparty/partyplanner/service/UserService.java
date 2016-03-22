package com.redparty.partyplanner.service;


import com.redparty.partyplanner.common.domain.User;

import java.util.List;

public interface UserService {

    User findUserById(Long id);

    User findByEmail(String email);

    User add(User user);

    User add(String email, String password, String name, String phone);

    List<User> findAll();

    void delete(Long id);

    boolean existsWithEmail(String email);
}
