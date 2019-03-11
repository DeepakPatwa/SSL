package com.stackroute.pie.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Field {
    private String fieldName;
    private String fieldValue;
}
