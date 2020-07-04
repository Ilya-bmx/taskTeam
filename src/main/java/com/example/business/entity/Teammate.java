package com.example.business.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "TEAMMATE")
public class Teammate {

    @Id
    @SequenceGenerator(name = "teammateSequence", sequenceName = "TEAMMATE_SEQUENCE", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "teammateSequence")
    @Column(name = "TEAMMATE_ID", updatable = false, nullable = false)
    private Long teammateId;
    @Column(name = "TEAMMATE_NAME")
    private String teammateName;
    @Column(name = "LASTNAME")
    private String lastName;
    @Column(name = "MIDDLENAME")
    private String middleName;
    @Column(name = "AGE")
    private byte age;
    @Column(name = "POSITION")
    private String position;
    @Column(name = "TEAM_NAME")
    private String teamName;
}
