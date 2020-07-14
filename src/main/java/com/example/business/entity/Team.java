package com.example.business.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TEAM")
public class Team {

    @Id
    @SequenceGenerator(name = "teamSequence", sequenceName = "TEAM_SEQUENCE", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "teamSequence")
    @Column(name = "TEAM_ID", updatable = false, nullable = false)
    private Long id;
    @Column(name = "TEAM_NAME", nullable = false)
    private String name;
    @Column(name = "TEAM_TYPE")
    private String type;
}
