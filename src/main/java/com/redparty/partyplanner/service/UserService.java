package com.redparty.partyplanner.service;


import com.redparty.partyplanner.common.domain.User;

import java.util.List;

public interface UserService {

    User findUserById(Long id);

    User add(User event);

    User add(String email, String password, String name);

    List<User> findAll();

    void delete(Long id);
}
