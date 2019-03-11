package com.stackroute.pie.controller;

import com.stackroute.pie.domain.FormFormat;
import com.stackroute.pie.services.AdminServices;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.Normalizer;
import java.util.List;
import java.util.Optional;

@Data
@Controller
@RequestMapping("/api/v1/")
@CrossOrigin("*")
public class AdminController {

    @Autowired
    private KafkaTemplate<String, FormFormat> kafkaTemplate;
    private AdminServices adminServices;
    @Autowired
    public AdminController(AdminServices adminServices) {
        this.adminServices = adminServices;
    }
    @PostMapping("formformat")
    public ResponseEntity<?> addNewFormFormat(@RequestBody FormFormat formFormat) {
        FormFormat savedFormFormat = adminServices.addNewFormFormat(formFormat);
//        kafkaTemplate.send("formFormats", savedFormFormat);
        return new ResponseEntity<FormFormat>(savedFormFormat, HttpStatus.CREATED);
    }
    @GetMapping("formformats")
    public ResponseEntity<?> getAllFormFormats() {
        List<FormFormat> listOfAllFormFormats = adminServices.getAllFormFormats();
        return new ResponseEntity<List<FormFormat>>(listOfAllFormFormats, HttpStatus.OK);
    }
    @GetMapping("formformat/{formId}")
    public ResponseEntity<Optional<FormFormat>> getFormFormat(@PathVariable(value = "formId", required = true) int formId) {
        Optional<FormFormat> retreivedFormFormat = adminServices.getFormFormat(formId);
        if(retreivedFormFormat == null)
            return new ResponseEntity<Optional<FormFormat>>(retreivedFormFormat, HttpStatus.OK);
        return new ResponseEntity<Optional<FormFormat>>(retreivedFormFormat, HttpStatus.OK);
    }
    @DeleteMapping("formformat/{formId}")
    public ResponseEntity<?> deleteFormFormat(@PathVariable(value = "formId", required = true) int formId) {
        adminServices.deleteFormFormat(formId);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
    @PutMapping("formformat/{formId}")
    public ResponseEntity<FormFormat> updateFormFormat(@PathVariable(value = "formId", required = true) int formId, @RequestBody FormFormat updatedFormFormat) {
        FormFormat newFormFormat = adminServices.updateFormFormat(formId, updatedFormFormat);
        return new ResponseEntity<FormFormat>(newFormFormat, HttpStatus.OK);
    }
    @GetMapping("formformat/name={formName}")
    public ResponseEntity<FormFormat> getFormByName(@PathVariable(value = "formName", required = true) String formName) {
        FormFormat formFormat = adminServices.getFormFormatByName(formName);
        return new ResponseEntity<FormFormat>(formFormat, HttpStatus.OK);
    }
    @GetMapping("formformat/search={formName}")
    public ResponseEntity<List<FormFormat>> searchFormByName(@PathVariable(value = "formName", required = true) String formName) {
        List<FormFormat> formFormat = adminServices.searchFormFormatByFormName(formName);
        return new ResponseEntity<List<FormFormat>>(formFormat, HttpStatus.OK);
    }
}
