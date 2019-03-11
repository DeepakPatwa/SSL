package com.stackroute.pie.controller;

import com.stackroute.pie.domain.Policy;
import com.stackroute.pie.exceptions.InsuredPoliciesNotFoundException;
import com.stackroute.pie.exceptions.InsurerNotFoundException;
import com.stackroute.pie.exceptions.PolicyAlreadyExistsException;
import com.stackroute.pie.exceptions.PolicyNotFoundException;
import com.stackroute.pie.repository.PolicyRepository;
import com.stackroute.pie.service.PolicyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("api/v1/")
@CrossOrigin(origins = "*")
public class PolicyController {


    private PolicyServiceImpl policyService;


    @Autowired
    PolicyController(PolicyServiceImpl policyService) {
        this.policyService = policyService;
    }

    //Adding the policy for insurer
    @PostMapping("policy")
    public ResponseEntity<?> addPolicy(@RequestBody Policy policy) {
        System.out.println(policy);
        ResponseEntity<?> responseEntity;
        try {
            Policy policy1 = policyService.addPolicy(policy);;
            responseEntity = new ResponseEntity<Policy>(policy1, HttpStatus.OK);
        } catch (PolicyAlreadyExistsException e) {
            responseEntity = new ResponseEntity<String>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
        return responseEntity;
    }

    //Deleting the policy for insurer insurerName and policyName
    @DeleteMapping("policy/{insurerName}/{policyName}")
    public ResponseEntity<?> deletePolicy(@PathVariable(value = "insurerName") String insurerName,
                                          @PathVariable(value = "policyName") String policyName) {

        ResponseEntity<?> responseEntity;
        try {
            Policy policy1 = policyService.deletePolicy(insurerName,policyName);
            responseEntity = new ResponseEntity<Policy>(policy1, HttpStatus.OK);
        } catch (PolicyNotFoundException e) {
            responseEntity = new ResponseEntity<String>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
        return responseEntity;
    }

    //Getting the policy for insurer
    @GetMapping("policy/{insurerLicense}")
    public ResponseEntity<?> getPolicy(@PathVariable(value = "insurerLicense") String insurerLicense) {
        ResponseEntity<?> responseEntity;
        try {
            List<Policy> policy = policyService.getPolicy(insurerLicense);
            responseEntity = new ResponseEntity<List<Policy>>(policy, HttpStatus.OK);
        } catch (InsurerNotFoundException ex) {
            responseEntity = new ResponseEntity<String>(ex.getMessage(),HttpStatus.NOT_FOUND);
        }
        return responseEntity;
    }

    //Get policies of Insured based on Insured Name
    @GetMapping("policies/{insuredName}")
    public ResponseEntity<?> getPolicyofInsured(@PathVariable(value = "insuredName") String insuredName) {
        ResponseEntity<?> responseEntity;
        try {
            List<Policy> policy = policyService.getPoliciesOfUser(insuredName);
            responseEntity = new ResponseEntity<List<Policy>>(policy, HttpStatus.OK);
        } catch (InsuredPoliciesNotFoundException ex) {
            responseEntity = new ResponseEntity<String>(ex.getMessage(),HttpStatus.NOT_FOUND);
        }
        return responseEntity;
    }

    //Getting the policy for insurerd for more details
    @GetMapping("policy/{insurerName}/{policyName}")
    public ResponseEntity<?> getPolicyforInsured(@PathVariable(value = "insurerName") String insurerName,
                                       @PathVariable(value = "policyName") String policyName) {
        ResponseEntity<?> responseEntity;
        try {
            Policy policy = policyService.getPolicyForUser(insurerName, policyName);
            responseEntity = new ResponseEntity<Policy>(policy, HttpStatus.OK);
        }catch(InsurerNotFoundException  ex){
            responseEntity = new ResponseEntity<String>(ex.getMessage(),HttpStatus.NOT_FOUND);
        } catch (PolicyNotFoundException ex) {
            responseEntity = new ResponseEntity<String>(ex.getMessage(),HttpStatus.NOT_FOUND);
        }
        return responseEntity;
    }

    //Added the insuredName into the policy
    @PutMapping("policy/{insurerName}/{policyName}/{insuredName}")
    public ResponseEntity<?> addInsured(@PathVariable(value = "insurerName") String insurerName,
                                        @PathVariable(value = "policyName") String policyName,
                                        @PathVariable(value = "insuredName") String insuredName) {
        ResponseEntity<?> responseEntity;
        try {
            Policy policy = policyService.addInsured(insurerName,policyName,insuredName);
            responseEntity = new ResponseEntity<Policy>(policy, HttpStatus.OK);
        } catch (InsurerNotFoundException e) {
            responseEntity = new ResponseEntity<String>(e.getMessage(),HttpStatus.NOT_FOUND);
        } catch (PolicyNotFoundException e) {
            responseEntity = new ResponseEntity<String>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
        return responseEntity;
    }

    //Delete the insuredName in the policy
    @DeleteMapping("policy/{insurerName}/{policyName}/{insuredName}")
    public ResponseEntity<?> deleteInsured(@PathVariable(value = "insurerName") String insurerName,
                                        @PathVariable(value = "policyName") String policyName,
                                        @PathVariable(value = "insuredName") String insuredName) {
        ResponseEntity<?> responseEntity;
        try {
            Policy policy = policyService.deleteInsured(insurerName,policyName,insuredName);
            responseEntity = new ResponseEntity<Policy>(policy, HttpStatus.OK);
        } catch (InsurerNotFoundException e) {
            responseEntity = new ResponseEntity<String>(e.getMessage(),HttpStatus.NOT_FOUND);
        } catch (PolicyNotFoundException e) {
            responseEntity = new ResponseEntity<String>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
        return responseEntity;
    }

    //Getting the insurerList for porting purpose
    @GetMapping("policy")
    public ResponseEntity<?> getInsurerList() {
        Set<String> insurerList = new HashSet<>();
        insurerList = policyService.getInsurerList();
        ResponseEntity<?> responseEntity;
        responseEntity = new ResponseEntity<Set<String>>(insurerList, HttpStatus.OK);
        return responseEntity;
    }
}
