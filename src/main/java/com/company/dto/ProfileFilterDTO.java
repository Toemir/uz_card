package com.company.dto;

import com.company.enums.ProfileRole;
import com.company.enums.ProfileStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ProfileFilterDTO {
    private String name;
    private String surname;
    private String middleName;
    private String username;
    private ProfileRole role;
    private ProfileStatus status;
    private String fromCreatedDate;
    private String toCreatedDate;
}
