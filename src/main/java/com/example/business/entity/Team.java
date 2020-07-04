package com.example.business.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TEAM")
public class Team {

    @Id
    @Column(name = "TEAM_NAME", updatable = false, nullable = false)
    private String name;
    @Column(name = "TEAM_TYPE")
    private String type;
    @OneToMany(fetch = LAZY, cascade = CascadeType.ALL)
    private List<Teammate> members = new ArrayList<>();
}
