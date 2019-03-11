package com.stackroute.pie.services;

import com.stackroute.pie.domain.FormFormat;
import com.stackroute.pie.repository.AdminRepository;
import me.xdrop.fuzzywuzzy.FuzzySearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class AdminServicesImpl implements AdminServices{
    private AdminRepository adminRepository;
    @Autowired
    public AdminServicesImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }
    public FormFormat addNewFormFormat(FormFormat formFormat) {
        FormFormat savedFormFormat = adminRepository.save(formFormat);
        return savedFormFormat;
    }
    @Override
    public List<FormFormat> getAllFormFormats() {
        List<FormFormat> allFormFormats = adminRepository.findAll();
        return allFormFormats;
    }

    @Override
    public void deleteFormFormat(int formId) {
        adminRepository.deleteById(formId);
    }

    @Override
    public Optional<FormFormat> getFormFormat(int formId) {
        if(adminRepository.existsById(formId) == false)
            return null;
        Optional<FormFormat> retrievedFormFormat = adminRepository.findById(formId);
        return retrievedFormFormat;
    }

    @Override
    public FormFormat updateFormFormat(int formId, FormFormat updatedFormFormat) {
        adminRepository.deleteById(formId);
        updatedFormFormat.setFormId(formId);
        FormFormat newFormFormat = adminRepository.save(updatedFormFormat);
        return newFormFormat;
    }

    @Override
    public FormFormat getFormFormatByName(String formName) {
        FormFormat formFormat = adminRepository.findByFormName(formName);
        return formFormat;
    }

    @Override
    public List<FormFormat> searchFormFormatByFormName(String formName) {
        List<FormFormat> fetchedFormFormats = adminRepository.findAll();
        List<FormFormat> filteredFormFormats = new ArrayList<>();
        List<FormFormat> exactSearchFormFormat = new ArrayList<>();
        for(FormFormat formFormat : fetchedFormFormats) {
            if(FuzzySearch.ratio(formName.toLowerCase(), formFormat.getFormName().toLowerCase()) == 100 ) {
                exactSearchFormFormat.add(formFormat);
                return exactSearchFormFormat;
            }
            if(FuzzySearch.ratio(formName.toLowerCase(), formFormat.getFormName().toLowerCase()) > 50) {
                filteredFormFormats.add(formFormat);
            }
        }
        return filteredFormFormats;
    }

}
