package com.company.dto;

import lombok.Getter;

@Getter
public class CardCreateAndUpdateDTO {
    private String phone;
    public Long balance;
    public String profileId;
    public String companyId;
}
