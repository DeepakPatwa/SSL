package com.stackroute.pie.controller;

import com.stackroute.pie.domain.PortingRequest;
import com.stackroute.pie.exceptions.RequestNotFoundException;
import com.stackroute.pie.repository.PortingRequestRepository;
import com.stackroute.pie.services.PortingRequestServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.service.ResponseMessage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/")
@CrossOrigin("*")
public class PortingRequestController {

    @Autowired
    private KafkaTemplate<String, PortingRequest> kafkaTemplate;

    PortingRequestServiceImpl requestService;

    PortingRequestRepository portingRequestRepository;
    @Autowired
    public PortingRequestController(PortingRequestServiceImpl requestService, PortingRequestRepository portingRequestRepository) {
        this.requestService = requestService;
        this.portingRequestRepository = portingRequestRepository;
    }

    //Method to store porting request details
    @PostMapping("request")
    public ResponseEntity postRequest (@RequestBody PortingRequest request) {
        ResponseEntity responseEntity;
        PortingRequest request1 = requestService.postRequest(request);

        responseEntity = new ResponseEntity<PortingRequest>(request1, HttpStatus.OK);
        return responseEntity;
    }

    //Method to update the request details
    @PutMapping("request")
    public ResponseEntity updateRequest (@RequestBody PortingRequest request){
        ResponseEntity responseEntity;

        try {

            PortingRequest request1 = requestService.updateRequest(request);
            responseEntity =  new ResponseEntity<PortingRequest>(request1, HttpStatus.OK);

        }
        catch (RequestNotFoundException ex) {
            responseEntity = new ResponseEntity<String>(ex.getMessage(),HttpStatus.CONFLICT);
            ex.printStackTrace();
        }
        return responseEntity;

    }

    //Method to delete porting request
    @DeleteMapping("request/{requestId}")
    public ResponseEntity deleteRequest (@PathVariable("requestId") int requestId){
        System.out.println("hi");
        ResponseEntity responseEntity;

        try {

            PortingRequest request1 = requestService.deleteRequest(requestId);
            responseEntity =  new ResponseEntity<PortingRequest>(request1, HttpStatus.OK);

        }
        catch (RequestNotFoundException ex) {
            responseEntity = new ResponseEntity<String>(ex.getMessage(),HttpStatus.CONFLICT);
            ex.printStackTrace();
        }
        return responseEntity;

    }

    //Method to display requests
    @GetMapping("request/{username}")
    public ResponseEntity<List<PortingRequest>> requestLists(@PathVariable("username") String insuredName) {
        ResponseEntity responseEntity;
        try {
            List<PortingRequest> allRequests = requestService.getRequests(insuredName);
            return new ResponseEntity<List<PortingRequest>>(allRequests, HttpStatus.OK);
        }
        catch (RequestNotFoundException ex) {
            responseEntity = new ResponseEntity<String>(ex.getMessage(),HttpStatus.CONFLICT);
            ex.printStackTrace();
        }
        return responseEntity;
    }

    //Method to display incoming porting requests
    @GetMapping("/incomingportingrequest/{newInsurerName}")
    public ResponseEntity<?> incomingPortingRequest( @PathVariable("newInsurerName") String newInsurerName) {
        if (portingRequestRepository.existsByNewInsurerName(newInsurerName) == false) {
            return new ResponseEntity<>("Insurer Not Found",
                    HttpStatus.NOT_FOUND);
        }
        List<PortingRequest> incomingPortingRequests = requestService.getIncomingPortingRequest(newInsurerName);
        List<PortingRequest> incomingPortingRequests1 = new ArrayList<>();
        System.out.println("In the incomingportingrequest");
        for(int i = 0;i < incomingPortingRequests.size(); i++) {
            if(incomingPortingRequests.get(i).getFromApproval() == 1 && incomingPortingRequests.get(i).getToApproval() == 0) {
                incomingPortingRequests1.add(incomingPortingRequests.get(i));
            }
        }
        return new ResponseEntity<List<PortingRequest>>(incomingPortingRequests1, HttpStatus.OK);
    }

    //Method to display outgoing porting requests
    @GetMapping("/outgoingportingrequest/{insurerName}")
    public ResponseEntity<?> outgoingPortingRequest(@PathVariable("insurerName") String insurerName) {
        if (portingRequestRepository.existsByInsurerName(insurerName) == false) {
            return new ResponseEntity<>("Insurer Not Found",
                    HttpStatus.NOT_FOUND);
        }
        List<PortingRequest> outgoingPortingRequests = requestService.getOutgoingPortingRequest(insurerName);
        List<PortingRequest> outgoingPortingRequests1 = new ArrayList<>();
        for(int i =0 ;i < outgoingPortingRequests.size(); i++) {
            if(outgoingPortingRequests.get(i).getFromApproval() != 1) {
                outgoingPortingRequests1.add(outgoingPortingRequests.get(i));
            }
        }

        return new ResponseEntity<List<PortingRequest>>(outgoingPortingRequests1, HttpStatus.OK);
    }

    //Method to accept outgoing requests
    @PutMapping("/acceptoutgoingportingrequest")
    public ResponseEntity<?> acceptOutgoingPortingRequest(@RequestBody PortingRequest portingRequest) {
        if (portingRequestRepository.existsByInsurerName(portingRequest.getInsurerName()) == false) {
            return new ResponseEntity<>("Insurer Not Found",
                    HttpStatus.NOT_FOUND);
        }
        PortingRequest acceptOutgoingPortingRequest = requestService.acceptOutgoingPortingRequest(portingRequest);
        requestService.deletePortingRequest(portingRequest);
        acceptOutgoingPortingRequest.setFromApproval(1);
        Date d = new Date();
        acceptOutgoingPortingRequest.setAcceptedDateofPreviousInsurer(d);
        PortingRequest portingRequest1 = requestService.postRequest(acceptOutgoingPortingRequest);
        return new ResponseEntity<PortingRequest>(portingRequest1, HttpStatus.OK);
    }

    //Method to accept outgoing requests
    @PutMapping("/acceptincomingportingrequest")
    public ResponseEntity<?> acceptIncomingPortingRequest(@RequestBody PortingRequest portingRequest) {
        if (portingRequestRepository.existsByNewInsurerName(portingRequest.getNewInsurerName()) == false) {
            return new ResponseEntity<>("Insurer Not Found",
                    HttpStatus.NOT_FOUND);
        }
        PortingRequest acceptIncomingPortingRequest = requestService.acceptIncomingPortingRequest(portingRequest);
        requestService.deletePortingRequest(portingRequest);
        acceptIncomingPortingRequest.setFromApproval(1);
        acceptIncomingPortingRequest.setToApproval(1);
        PortingRequest portingRequest1 = requestService.postRequest(acceptIncomingPortingRequest);
        kafkaTemplate.send("incomingporting", portingRequest1);
        return new ResponseEntity<PortingRequest>(portingRequest1, HttpStatus.OK);
    }

    //Method to reject incoming requests
    @PutMapping("/rejectincomingportingrequest")
    public ResponseEntity<?> rejectIncomingPortingRequest(@RequestBody PortingRequest portingRequest) {
        if (portingRequestRepository.existsByNewInsurerName(portingRequest.getNewInsurerName()) == false) {
            return new ResponseEntity<>("Insurer Not Found",
                    HttpStatus.NOT_FOUND);
        }
        PortingRequest rejectIncomingPortingRequest = requestService.rejectIncomingPortingRequest(portingRequest);
        requestService.deletePortingRequest(portingRequest);
        rejectIncomingPortingRequest.setFromApproval(1);
        rejectIncomingPortingRequest.setToApproval(2);
        PortingRequest portingRequest1 = requestService.postRequest(rejectIncomingPortingRequest);
        return new ResponseEntity<PortingRequest>(portingRequest1, HttpStatus.OK);
    }
    @GetMapping("outgoingportingrequest/portingRequestId={portingRequestId}")
    public ResponseEntity<?> getPortingRequestByPortingRequestId(@PathVariable (value = "portingRequestId", required = true) int portingRequestId) {
        return new ResponseEntity<Optional<PortingRequest>>(requestService.getPortingRequestByPortingRequestId(portingRequestId), HttpStatus.OK);
    }

}