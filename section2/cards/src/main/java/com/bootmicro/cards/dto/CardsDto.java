package com.bootmicro.cards.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class CardsDto
{
    private String mobileNumber;
    private String cardNumber;
    private String cardType;
    private Long totalLimit;
    private Long amountUsed;
    private Long availableAmount;
}
