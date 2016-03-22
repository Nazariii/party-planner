package com.redparty.partyplanner.common.domain.dto;

import com.redparty.partyplanner.common.domain.User;
import org.springframework.security.core.authority.AuthorityUtils;

public class UserSecurityDTO extends org.springframework.security.core.userdetails.User {

    private User user;

    public UserSecurityDTO(User user) {
        // todo AuthorityUtils.createAuthorityList(user.getRole().toString())
        super(user.getEmail(), user.getPassword(), AuthorityUtils.createAuthorityList("ROLE_SUPER_ADMIN"));
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public Long getId() {
        return user.getId();
    }

}