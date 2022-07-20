package com.company.dto;

import com.company.enums.ClientStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientDTO {
    private String id;
    public String name;
    public String surname;
    public String middleName;
    public String phone;
    public ClientStatus status;
    private String passportSeries;
    private String passportNumber;
    public Boolean visible;
    public LocalDateTime createdDate;
}
