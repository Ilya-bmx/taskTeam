package com.example.business.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TeammateModel {

    private Long id;
    private String name;
    private String lastName;
    private String middleName;
    private byte age;
    private String position;
    private String teamName;
}
