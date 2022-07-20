package com.company.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class ClientCreateOrUpdateDTO {
    @NotBlank(message = "Name must not be empty")
    private String name;
    @NotBlank(message = "Surname must not be empty")
    private String surname;
    @NotBlank(message = "Middle name must not be empty")
    private String middleName;
    @NotBlank(message = "Phone must not be empty")
    private String phone;
    @NotBlank(message = "Passport series must not be empty")
    private String passportSeries;
    @NotBlank(message = "Passport number must not be empty")
    private String passportNumber;

}
