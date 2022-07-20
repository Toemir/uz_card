package com.company.dto;

import com.company.enums.CardStatus;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class CardStatusDTO {
    @NotNull(message = "Status must not be empty")
    private CardStatus status;
}
