package com.redparty.partyplanner.common.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ServiceDTO {

    @NotEmpty
    private String name;

    @NotEmpty
    private String userId;

}
