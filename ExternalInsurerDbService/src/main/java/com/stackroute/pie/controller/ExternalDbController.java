package com.stackroute.pie.controller;

import com.stackroute.pie.domain.InsurerPolicy;
import com.stackroute.pie.service.ExternalDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1")
public class ExternalDbController {

    private ExternalDbService externalDbService;
    @Autowired
    public ExternalDbController(ExternalDbService externalDbService) {
        this.externalDbService = externalDbService;
    }




    @GetMapping("/policy/external/{insurerName}")
    public ResponseEntity<?> getPolicies(@PathVariable(value = "insurerName") String insurerName) throws UnsupportedEncodingException, SQLException, ClassNotFoundException {
        System.out.println(insurerName + "####");
       List<InsurerPolicy> insurer = externalDbService.getPolicies(insurerName);
        return new ResponseEntity<List<InsurerPolicy>>(insurer, HttpStatus.OK);
    }




}
