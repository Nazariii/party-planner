package com.redparty.partyplanner.common.domain.dto;

import com.redparty.partyplanner.common.domain.User;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

@Getter
@Setter
public class UserCreationDTO {

    @NotEmpty
    private String name;

    @NotEmpty
    private String email;

    @NotEmpty
    private String password;

    @NotEmpty
    private String passwordRepeated;

    @NotEmpty
    private String phone;

    private String address;


}
