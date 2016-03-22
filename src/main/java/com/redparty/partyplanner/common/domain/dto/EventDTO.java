package com.redparty.partyplanner.common.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventDTO {

    @NotEmpty
    private String name;

    private String eventStatus;

    @NotEmpty
    private String userId;

}
