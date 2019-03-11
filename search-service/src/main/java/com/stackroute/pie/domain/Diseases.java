package com.stackroute.pie.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "disease")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Diseases {
    public String diseaseName;
}
