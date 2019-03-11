package com.stackroute.pie.services;

import com.stackroute.pie.domain.Insurer;
import com.stackroute.pie.domain.Policy;
import com.stackroute.pie.domain.PremiumCalci;
import com.stackroute.pie.message.request.SignUpForm;

import java.util.List;

public interface InsurerService {
    Insurer addNewPolicy(Policy policy);
    List<Policy> getPolicies(String insurerLicense);
    Insurer deletePolicy(String insurerName, long policyId);
    Insurer addInsurer(SignUpForm signUpForm);
    long calculatePremium(PremiumCalci premiumCalci);
}
