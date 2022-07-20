package com.company.dto;

import com.company.enums.ProfileRole;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class ProfileCreateAndUpdateDTO {
    @NotBlank(message = "Name must not be empty")
    private String name;
    @NotBlank(message = "Surname must not be empty")
    private String surname;
    @NotBlank(message = "Middle name must not be empty")
    private String middleName;
    @NotBlank(message = "Username must not be empty")
    private String username;
    @NotBlank(message = "Password must not be empty")
    @Size(min = 3,message = "Password must contain at least 3 characters")
    private String password;
    @NotNull(message = "Role must not be empty")
    private ProfileRole role;
}
