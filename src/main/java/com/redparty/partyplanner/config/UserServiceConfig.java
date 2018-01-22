package com.redparty.partyplanner.config;

import com.redparty.partyplanner.common.domain.User;
import com.redparty.partyplanner.common.domain.dto.UserSecurityDTO;
import com.redparty.partyplanner.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserServiceConfig implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public UserServiceConfig(UserService userService) {
        this.userService = userService;
    }

    @Override
    // todo add not found user exception handling
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByEmail(username);
        return new UserSecurityDTO(user);
    }
}
