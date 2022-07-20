package com.company.dto;

import com.company.enums.CompanyRole;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CompanyCreateAndUpdateDTO {
    @NotBlank(message = "Name must not be empty")
    private String name;

    @NotBlank(message = "Address must not be empty")
    private String address;

    @NotBlank(message = "Contact must not be empty")
    private String contact;

    @NotBlank(message = "Username must not be empty")
    private String username;

    @NotBlank(message = "Password must not be empty")
    private String password;

    @NotNull(message = "Role must not be empty")
    private CompanyRole role;
}
