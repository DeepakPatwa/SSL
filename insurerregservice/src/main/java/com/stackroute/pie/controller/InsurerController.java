package com.stackroute.pie.controller;

import com.stackroute.pie.domain.*;
import com.stackroute.pie.message.request.SignUpForm;
import com.stackroute.pie.message.response.ResponseMessage;
import com.stackroute.pie.repository.InsurerRepository;
import com.stackroute.pie.services.InsurerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1")
public class InsurerController {

    @Autowired
    InsurerRepository insurerRepository;

    @Autowired
    InsurerServiceImpl insurerService;

    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();


    @Autowired
    private KafkaTemplate<String, Insurer> kafkaTemplate;

    //Method for registering a new Insurer
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpForm signUpRequest) {
        if (insurerRepository.existsByInsurerLicense(signUpRequest.getInsurerLicense())) {
            return new ResponseEntity<>(new ResponseMessage("Fail -> Username is already taken!"),
                    HttpStatus.CONFLICT);
        }

        if (insurerRepository.existsByInsurerEmail(signUpRequest.getInsurerEmail())) {
            return new ResponseEntity<>(new ResponseMessage("Fail -> Email is already in use!"),
                    HttpStatus.CONFLICT);
        }
        signUpRequest.setPassword(bCryptPasswordEncoder.encode(signUpRequest.getPassword()));
        //Calling InsurerService to add into the MongoDB
        Insurer insurer = insurerService.addInsurer(signUpRequest);
        //Kafka producing the Insurer pojo ,so that login microservice can consume it
        kafkaTemplate.send("company_1_json",insurer);
        return new ResponseEntity<>(new ResponseMessage("Insurer registered successfully!"), HttpStatus.CREATED);
    }

    //Method for adding a new policy for existing insurer
    @PutMapping("/policy/newpolicy")
    public ResponseEntity<?> addNewPolicy(@RequestBody Policy insurerPolicy){
        if (!insurerRepository.existsByInsurerName(insurerPolicy.getInsurerName())) {
            return new ResponseEntity<>(new ResponseMessage("Insurer Not Found"),
                    HttpStatus.NOT_FOUND);
        }
        Insurer insurer = insurerService.addNewPolicy(insurerPolicy);
        kafkaTemplate.send("insurer_policy_json",insurer);
        return new ResponseEntity<Insurer>(insurer, HttpStatus.CREATED);
    }

    //Method for displaying the exiting policy
    @GetMapping("/policy/display/{insurerLicense}")
    public ResponseEntity<?> getPolicies(@PathVariable(value = "insurerLicense") String insurerLicense){
        if (!insurerRepository.existsByInsurerLicense(insurerLicense)) {
            return new ResponseEntity<>(new ResponseMessage("Insurer Not Found"),
                    HttpStatus.NOT_FOUND);
        }
        List<Policy> policies = insurerService.getPolicies(insurerLicense);
        return new ResponseEntity<List<Policy>>(policies, HttpStatus.OK);
    }

    //Method for deleting an existing policy
    @PutMapping("/policy/delete/{insurerName}/{policyId}")
    public ResponseEntity<?> deletePolicy(@PathVariable(value = "insurerName") String insurerName, @PathVariable(value = "policyId") long policyId){
        if (insurerRepository.existsByInsurerName(insurerName) == false) {
            return new ResponseEntity<>(new ResponseMessage("Insurer Not Found"),
                    HttpStatus.NOT_FOUND);
        }

        System.out.println("InsurerLicence : "+insurerName + " " +"policyId : "+policyId);
        Insurer insurer = insurerService.deletePolicy(insurerName,policyId);
        return new ResponseEntity<Insurer>(insurer, HttpStatus.OK);
    }

    @PostMapping("/policy/premium/calculator")
    public ResponseEntity<?> calculatePremium(@RequestBody PremiumCalci premiumCalci){
        System.out.println("entered controller");
        System.out.println(premiumCalci.toString());
        long premium = insurerService.calculatePremium(premiumCalci);
        String pr = Long.toString(premium);
        return new ResponseEntity<String>(pr, HttpStatus.OK);

    }
}
