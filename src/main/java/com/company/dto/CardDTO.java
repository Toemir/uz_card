package com.company.dto;

import com.company.enums.CardStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CardDTO {
    private String id;
    private String number;
    private String profileId;
    private ProfileDTO profile;
    private String companyId;
    private CompanyDTO company;
    private Long balance;
    private String phone;
    private CardStatus status;
    private Boolean visible;
    private LocalDateTime createdDate;
    private LocalDateTime expiredDate;
}
