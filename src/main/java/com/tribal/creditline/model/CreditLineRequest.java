package com.tribal.creditline.model;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class CreditLineRequest {
    private String user;

    @NotNull(message = "foundingType may not be null")
    @NotEmpty(message = "foundingType may not be empty")
    private String foundingType;

    @NotNull(message = "cashBalance may not be null")
    private Double cashBalance;

    @NotNull(message = "monthlyRevenue may not be null")
    private Double monthlyRevenue;

    @NotNull(message = "requestedCreditLine may not be null")
    private Double requestedCreditLine;

    @NotNull(message = "requestedDate may not be null")
    private Date requestedDate;
}
