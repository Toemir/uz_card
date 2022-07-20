package com.company.dto;

import com.company.enums.ProfileRole;
import com.company.enums.ProfileStatus;
import com.company.service.ProfileService;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileDTO {
    private String id;
    private String name;
    private String username;
    private String surname;
    private String middleName;
    private ProfileRole role;
    private ProfileStatus status;
    private LocalDateTime createdDate;
    private Boolean visible;
}
