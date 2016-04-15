package com.redparty.partyplanner.service;


import com.redparty.partyplanner.common.domain.User;
import com.redparty.partyplanner.config.constant.AppConstant;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

public interface UserService {

    @Cacheable(AppConstant.USER_CACHE_NAME)
    User findUserById(Long id);

    User findByEmail(String email);

    @CachePut(value = AppConstant.USER_CACHE_NAME, key = "#result.id")
    User add(User user);

    @CachePut(value = AppConstant.USER_CACHE_NAME, key = "#result.id")
    User add(String email, String password, String name, String phone);

    List<User> findAll();

    @CacheEvict(AppConstant.USER_CACHE_NAME)
    void delete(Long id);

    boolean existsWithEmail(String email);
}
