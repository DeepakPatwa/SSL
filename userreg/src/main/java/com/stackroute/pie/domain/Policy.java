package com.stackroute.pie.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Policy {

    private long policyId;
    private String insuredName;
    private String insurerName;
    private String policyName;
    private String diseasesCovered;
    private long SumInsured;
    private int waitingPeriod;
    private int monthlyPremium;
    private int yearlyPremium;

}