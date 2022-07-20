package com.company.dto;

import com.company.enums.ClientStatus;
import com.company.service.ClientService;
import lombok.Getter;

@Getter
public class ClientFilterDTO {
    public String name;
    public String surname;
    public String middleName;
    public String phone;
    public String passportSeries;
    public String passportNumber;
    public ClientStatus status;
    public String fromCreatedDate;
    public String toCreatedDate;

}
