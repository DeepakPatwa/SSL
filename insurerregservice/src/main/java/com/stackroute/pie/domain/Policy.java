package com.stackroute.pie.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "policies")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Policy {
    @NotBlank
    @UniqueElements
    private int policyId;
    @NotBlank
    private String insurerName;
    @NotBlank
    @UniqueElements
    private String policyName;
    @NotBlank
    private long minSumInsured;
    @NotBlank
    private long maxSumInsured;
    private int noOfCashLessHospitals;
    @NotBlank
    private int minAge;
    @NotBlank
    private int maxAge;
    @NotBlank
    private int waitingPeriod;
}
