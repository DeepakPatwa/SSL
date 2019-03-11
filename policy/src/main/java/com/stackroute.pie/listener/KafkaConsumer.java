package com.stackroute.pie.listener;

import com.stackroute.pie.domain.Policy;
import com.stackroute.pie.domain.PortingRequest;
import com.stackroute.pie.exceptions.InsurerNotFoundException;
import com.stackroute.pie.exceptions.PolicyNotFoundException;
import com.stackroute.pie.repository.PolicyRepository;
import com.stackroute.pie.service.PolicyService;
import io.swagger.models.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {
    PolicyService policyService;
    @Autowired
    KafkaConsumer(PolicyService policyService) {
        this.policyService = policyService;
    }
    @KafkaListener(topics = "incomingporting", groupId = "group1_json", containerFactory = "userKafkaListenerFactory")
    public void consumeJson(PortingRequest portingRequest) throws InsurerNotFoundException, PolicyNotFoundException {
        System.out.println("yoyoyoyoyo");
        System.out.println("Consumed JSON Message: " + portingRequest);
        String insurerX = portingRequest.getInsurerName();
        String insurerY = portingRequest.getNewInsurerName();
        String policyX = portingRequest.getInsurerProduct();
        String policyY = portingRequest.getNewInsurerProduct();
        String insuredName = portingRequest.getInsuredName();

        Policy policy =  policyService.deleteInsured(insurerX,policyX,insuredName);
        Policy policy1 = policyService.addInsured(insurerY,policyY,insuredName);

    }
}
