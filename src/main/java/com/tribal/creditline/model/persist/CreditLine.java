package com.tribal.creditline.model.persist;

import javax.persistence.Id;
import javax.persistence.Entity;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Data;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreditLine {

    @Id
    private String user;
    private Boolean accepted;
    private Double acceptedCreditLine;
    private Integer timesRejected;
}

