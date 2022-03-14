package com.tribal.creditline.model;

import lombok.Data;

@Data
public class CreditLineAcceptance {
    private Boolean accepted;
    private Double acceptedCreditLine;
    private Integer timesRejected;
}
