package com.stackroute.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Document(collection = "pending_tasks")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PendingTasks {
    @Id
    private int pendingTasksId;
    private List<Task> taskList;
    private int portingRequestId;
    private String newInsurerName;
    @NotBlank
    private String insuredName;
    @NotBlank
    private String insurerName;

//    @NotBlank
//    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
//    private Date dateOfBirth;
//    @NotBlank
//    private String insuredAddress;
//    @NotBlank
//    private String insurerProduct;
//    @NotBlank
//    private int sumInsured;
//    @NotBlank
//    private int cumulativeBonus;
//    @NotBlank
//    private String[] addOns;
//    @NotBlank
//    private int policyNumber;
//    @NotBlank
//    private String newInsurerName;
//    @NotBlank
//    private String newInsurerProduct;
//    @NotBlank
//    private int newSumInsured;
//    @NotBlank
//    private int newCumulativeBonus;
//    @NotBlank
//    private String reasonForPortability;
//    private int familyMembers;
//    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
//    private Date createDate = new Date();
//    @NotBlank
//    private boolean exclusionPeriod;
//    private int fromApproval;
//    private int toApproval;
//    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
//    private Date acceptedDateofPreviousInsurer;
}
