package com.example.business.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeamModel {

    @NotNull(message = "Empty Id")
    private Long id;
    private String type;
    @NotNull(message = "Empty teamName")
    private String name;
    private List<TeammateModel> members;
}
