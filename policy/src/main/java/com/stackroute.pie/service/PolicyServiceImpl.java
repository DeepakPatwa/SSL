package com.stackroute.pie.service;

import com.stackroute.pie.domain.Policy;
import com.stackroute.pie.exceptions.InsuredPoliciesNotFoundException;
import com.stackroute.pie.exceptions.InsurerNotFoundException;
import com.stackroute.pie.exceptions.PolicyAlreadyExistsException;
import com.stackroute.pie.exceptions.PolicyNotFoundException;
import com.stackroute.pie.repository.PolicyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PolicyServiceImpl implements PolicyService {

    private PolicyRepository policyRepository;
    private KafkaTemplate<String, Policy> kafkaTemplate;

    @Autowired
    PolicyServiceImpl(PolicyRepository policyRepository,KafkaTemplate<String, Policy> kafkaTemplate) {
        this.policyRepository = policyRepository;
        this.kafkaTemplate = kafkaTemplate;
    }
    //Method for adding the policy for insurer
    @Override
    public Policy addPolicy(Policy policy) throws PolicyAlreadyExistsException {
        if(policyRepository.existsByPolicyIdAndInsurerName(policy.getPolicyId(),policy.getInsurerName())) {
            new PolicyAlreadyExistsException();
        }

        Policy policy1 = new Policy(policy.getInsurerName(),policy.getInsurerLicense(),policy.getPolicyName(),
                policy.getPolicyId(),policy.getMinAge(),policy.getMaxAge(),policy.getMinSumInsured(),
                policy.getMaxSumInsured(), policy.getPolicyDescription(), policy.getPolicyType(),
                policy.getGenderAvail(), policy.getWaitingPeriod(),policy.getPolicyTerm(), new ArrayList<>());
        policyRepository.save(policy1);
        Policy policy2 = policyRepository.findByUniqueId(policy1.getUniqueId()).get();
        System.out.println(policy2);
        kafkaTemplate.send("policy_added",policy2);
        return policy2;
    }

    //Method for deleting the policy for insurer
    @Override
    public Policy deletePolicy(String insurerName, String policyName) throws PolicyNotFoundException {
        if(policyRepository.existsByPolicyNameAndInsurerName(policyName,insurerName) == false) {
            new PolicyNotFoundException();
        }
        String uniqueId = insurerName + policyName;
        Policy policy = policyRepository.findByUniqueId(uniqueId).get();
        Policy policy1 = policy;
        policyRepository.delete(policy);
        return policy1;
    }

    //Method for getting the policy for insurer
    @Override
    public List<Policy> getPolicy(String insurerLicense) throws InsurerNotFoundException{
        if(policyRepository.existsByInsurerLicense(insurerLicense)) {
            List<Policy> policies= policyRepository.findByInsurerLicense(insurerLicense).get();
            return policies;
        }
        else {
            return (List<Policy>) new InsurerNotFoundException();
        }
    }

    //Method to find policy based on insurerName and policyName
    @Override
    public Policy getPolicyForUser(String insurerName, String policyName)
            throws InsurerNotFoundException,PolicyNotFoundException {

        if (policyRepository.existsByInsurerName(insurerName)) {
            if (policyRepository.existsByPolicyNameAndInsurerName(policyName, insurerName)) {
                Policy policy = policyRepository.findByInsurerNameAndPolicyName(insurerName, policyName).get();
                return policy;
            }
            else  {
                throw new PolicyNotFoundException();
            }
        }
        else {
            throw new InsurerNotFoundException();
        }
    }

    //Method for adding the inusredName
    @Override
    public Policy addInsured(String insurerName, String policyName, String insuredName)
            throws InsurerNotFoundException,PolicyNotFoundException{
        if(policyRepository.existsByInsurerName(insurerName)) {
            if(policyRepository.existsByPolicyNameAndInsurerName(policyName,insurerName)) {
                Policy policy = policyRepository.findByUniqueId(insurerName+policyName).get();
                List<String> insuredList = policy.getInsuredList();
                insuredList.add(insuredName);
                policy.setInsuredList(insuredList);
                policyRepository.save(policy);
                return policyRepository.findByInsurerNameAndPolicyName(insurerName,policyName).get();
            }
            else {
                throw new PolicyNotFoundException();
            }
        }
        else {
            throw new InsurerNotFoundException();
        }
    }

    //Method for deleting the insuredName from the policy
    @Override
    public Policy deleteInsured(String insurerName, String policyName, String insuredName)
            throws InsurerNotFoundException,PolicyNotFoundException{
        if(policyRepository.existsByInsurerName(insurerName)) {
            if(policyRepository.existsByPolicyNameAndInsurerName(policyName,insurerName)) {
                Policy policy = policyRepository.findByUniqueId(insurerName+policyName).get();
                List<String> insuredList = policy.getInsuredList();
                insuredList.remove(insuredName);
                policy.setInsuredList(insuredList);
                policyRepository.save(policy);
                return policyRepository.findByInsurerNameAndPolicyName(insurerName,policyName).get();
            }
            else {
                throw new PolicyNotFoundException();
            }
        }
        else {
            throw new InsurerNotFoundException();
        }
    }

    //Method to get the policies of the User
    @Override
    public List<Policy> getPoliciesOfUser(String insuredName)
            throws InsuredPoliciesNotFoundException {
        List<Policy> policies = policyRepository.findAll();
        List<Policy> insuredPolicies = new ArrayList<>();
        for(int i = 0; i < policies.size(); i++) {
            Policy policy = policies.get(i);
            if(policy.getInsuredList().contains(insuredName) == true) {
                insuredPolicies.add(policy);
            }
        }
        if(insuredPolicies.size() == 0) {
            throw new InsuredPoliciesNotFoundException();
        }
        else {
            return insuredPolicies;
        }
    }

    //Method to get Insurer Names
    @Override
    public Set<String> getInsurerList() {
        Set<String> set = new HashSet<String>();
        List<Policy> policies = policyRepository.findAll();
        for(int i = 0; i < policies.size(); i++) {
            set.add(policies.get(i).getInsurerName());
        }
        return set;
    }
}
