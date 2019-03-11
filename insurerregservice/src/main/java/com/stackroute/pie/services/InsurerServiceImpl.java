package com.stackroute.pie.services;

import com.stackroute.pie.domain.*;
import com.stackroute.pie.message.request.SignUpForm;
import com.stackroute.pie.repository.InsurerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class InsurerServiceImpl implements  InsurerService{

    private InsurerRepository insurerRepository;
  
    private PasswordEncoder passwordEncoder;

    @Autowired
    public InsurerServiceImpl(InsurerRepository insurerRepository) {
        this.insurerRepository = insurerRepository;
        //this.passwordEncoder = passwordEncoder;
    }

    public Insurer addNewPolicy(Policy policy) {
        String name = policy.getInsurerName();
        Insurer insurer1 = insurerRepository.findByInsurerName(name).get();
        List<Policy> policies = new ArrayList<>();
        policies = insurerRepository.findByInsurerName(name).get().getPolicies();
        policies.add(policy);
        insurer1.setPolicies(policies);
        insurerRepository.deleteByInsurerName(name);
        insurerRepository.save(insurer1);
        return insurerRepository.findByInsurerName(name).get();
    }


    public List<Policy> getPolicies(String insurerLicense) {
        Insurer insurer1 = insurerRepository.findByInsurerLicense(insurerLicense).get();
        return insurer1.getPolicies();
    }


    public Insurer deletePolicy(String insurerName,long policyId) {
        Insurer insurer1 = insurerRepository.findByInsurerName(insurerName).get();
        List<Policy> policies = insurer1.getPolicies();
        List<Policy> newPolicy  = new ArrayList<>();
        for(Policy p:policies)
        {
            if(p.getPolicyId() != policyId){
                newPolicy.add(p);
            }
        }
        insurer1.setPolicies(newPolicy);
        insurerRepository.deleteByInsurerName(insurer1.getInsurerName());
        insurerRepository.save(insurer1);

        return insurer1;

    }

    @Override
    public Insurer addInsurer(SignUpForm signUpRequest) {
        List<Policy> policies = new ArrayList<>();
        Insurer insurer = new Insurer(signUpRequest.getInsurerName(),signUpRequest.getInsurerLicense(),signUpRequest.getInsurerEmail(),signUpRequest.getPassword(),signUpRequest.getInsurerAddress(),signUpRequest.getSecurityQuestion(),signUpRequest.getSecurityAnswer(),policies);
        insurer.setPolicies(policies);
        System.out.println("service" +insurer);
        Set<Role> roles = new HashSet<>();
        Role userrRole = new Role();
        userrRole.setName(RoleName.ROLE_INSURER);
        roles.add(userrRole);
        insurer.setRoles(roles);
        Insurer insurer1 = insurerRepository.save(insurer);
        return insurer1;
    }

    @Override
    public long calculatePremium(PremiumCalci premiumCalci){

        long premium = 1000;
        if (premiumCalci.getAgeOfEldest() < 40) {
            premium *= 1.1;

        }
        else{
            // Add 20% per 5 years above 40
            int age = premiumCalci.getAgeOfEldest() - 40;
            while (age >= 5) {
                premium *= 1.2;
                age -= 5;
            }

        }
        if (premiumCalci.getPolicyName().equals("Apollo-cancer") ) {
            premium *= 1.3;
        }
        if (premiumCalci.getPolicyName().equals("MaxBupa-cancer") ) {
            premium *= 1.2;
        }
        if (premiumCalci.getPolicyName().equals("StarHealth-cancer") ) {
            premium *= 1.1;
        }


        if (premiumCalci.getPolicyName().equals( "Apollo-children")) {
            premium *= 1.2;
        }
        if (premiumCalci.getPolicyName().equals("MaxBupa-children") ) {
            premium *= 1.3;
        }
        if (premiumCalci.getPolicyName() .equals("StarHealth-children") ) {
            premium *= 1.5;
        }


        if (premiumCalci.getPolicyName().equals("Apollo-Family") ) {
            premium *= 1.075;
        }
        if (premiumCalci.getPolicyName().equals("MaxBupa-Family") ) {
            premium *= 1.15;
        }
        if (premiumCalci.getPolicyName().equals("StarHealth-Family") ) {
            premium *= 1.1;
        }


        if (premiumCalci.getSumInsured() == 100000) {
            premium *= 1.1;
        }
        if (premiumCalci.getSumInsured() == 200000) {
            premium *= 1.2;
        }
        if (premiumCalci.getSumInsured() == 300000) {
            premium *= 1.3;
        }
        if (premiumCalci.getSumInsured() == 400000) {
            premium *= 1.4;
        }
        if (premiumCalci.getSumInsured() == 500000) {
            premium *= 1.5;
        }  if (premiumCalci.getSumInsured() == 600000) {
            premium *= 1.6;
        }


        premium*= premiumCalci.getNoOfAdults();
        premium+= (premiumCalci.getNoOfChildren()*.5);

        premium*= premiumCalci.getNoOfYears();

        return premium;

    }
}
