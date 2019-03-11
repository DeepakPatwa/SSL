package com.stackroute.pie.services;

import com.stackroute.pie.domain.FormFormat;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface AdminServices {
    public FormFormat addNewFormFormat(FormFormat formFormat);
    public List<FormFormat> getAllFormFormats();
    public void deleteFormFormat(int formId);
    public Optional<FormFormat> getFormFormat(int formId);
    public FormFormat updateFormFormat(int formId, FormFormat updatedFormFormat);
    public FormFormat getFormFormatByName(String formName);
    public List<FormFormat> searchFormFormatByFormName(String formName);
}
