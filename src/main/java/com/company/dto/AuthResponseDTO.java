package com.company.dto;

import com.company.enums.GeneralRole;
import com.company.enums.GeneralStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthResponseDTO {
    private String id;
    private String username;
    private String password;
    private GeneralRole role;
    private String jwt;
}
