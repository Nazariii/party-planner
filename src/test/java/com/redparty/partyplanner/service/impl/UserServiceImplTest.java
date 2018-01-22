package com.redparty.partyplanner.service.impl;

import com.redparty.partyplanner.MockitoExtension;
import com.redparty.partyplanner.common.domain.User;
import com.redparty.partyplanner.common.exception.ResourceNotFoundException;
import com.redparty.partyplanner.repository.UserRepository;
import com.redparty.partyplanner.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    public static final String TEST_EMAIL = "testEmail";
    public static final String USERNAME = "username";
    public static final User TEST_USER = new User(TEST_EMAIL, "pass", USERNAME, "phone");

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private UserService userService = new UserServiceImpl();


    @Test
    @DisplayName("successful user retrieving by id")
    void findUserByIdTest() {
        Long userId = 1L;
        when(userRepository.findOneById(eq(userId))).thenReturn(Optional.of(TEST_USER));
        User resultUser = userService.findUserById(userId);
        assertThat(resultUser.getName(), is(USERNAME));
    }

    @Test
    @DisplayName("user not found when retrieving by id")
    void notFoundUserByIdTest() {
        Long userId = 1L;
        when(userRepository.findOneById(eq(userId))).thenReturn(Optional.empty());
        Throwable exception = assertThrows(ResourceNotFoundException.class, () -> userService.findUserById(userId));
        assertThat(exception.getMessage(), is("Resource 'User' with 'id' = '1' was not found"));
    }

    @Test
    @DisplayName("successful user retrieving by email")
    void findByEmail() {
        when(userRepository.findByEmail(eq(TEST_EMAIL))).thenReturn(Optional.of(TEST_USER));
        User resultUser = userService.findByEmail(TEST_EMAIL);
        assertThat(resultUser, equalTo(TEST_USER));
    }

    @Test
    @DisplayName("user not found by email")
    void notFoundByEmail() {
        when(userRepository.findByEmail(eq(TEST_EMAIL))).thenReturn(Optional.empty());
        Throwable exception = assertThrows(ResourceNotFoundException.class, () -> userService.findByEmail(TEST_EMAIL));
        assertThat(exception.getMessage(), is("Resource 'User' with 'email' = 'testEmail' was not found"));
    }
/*
    @Test
    void add() {
    }

    @Test
    void add1() {
    }

    @Test
    void findAll() {
    }

    @Test
    void delete() {
    }

    @Test
    void existsWithEmail() {
    }*/
}