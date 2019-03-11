package com.stackroute.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jdk.jfr.BooleanFlag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.SimpleDateFormat;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    private String taskName;
    private String taskDescription;
    private Boolean taskStatus;
    private String dueDate;
}
