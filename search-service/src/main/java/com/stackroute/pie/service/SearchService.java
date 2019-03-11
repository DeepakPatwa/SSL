package com.stackroute.pie.service;

import com.stackroute.pie.domain.Diseases;
import com.stackroute.pie.domain.Policy;

import java.util.List;

public interface SearchService {
    public List<Policy> getAllPolicies(String value);
    public Policy savePolicy(Policy policy);
    public List<Policy> tokenString(String value) throws Exception;
    public List<Policy> getByDisease(String disease);
}
