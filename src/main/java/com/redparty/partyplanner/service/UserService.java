package com.redparty.partyplanner.service;


import com.redparty.partyplanner.common.domain.User;

import java.util.List;

public interface UserService {

    User findUserById(Long id);

    void add(User event);

    void add(String email, String password, String name);

    List<User> findAll();

    User delete(Long id);
}
