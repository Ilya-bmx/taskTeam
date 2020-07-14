package com.example.business.service;

import com.example.business.entity.Team;
import com.example.business.model.TeamModel;
import com.example.business.model.TeammateModel;
import com.example.business.repo.TeamRepository;
import com.example.business.repo.TeammateRepository;
import com.example.business.service.mappers.TeamMapper;
import com.example.config.DBConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.transaction.TestTransaction;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = DBConfig.class)
@RunWith(SpringRunner.class)
class TeamServiceTest {

    private TeamService teamService;

    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private TeammateRepository teammateRepository;

    private TeamMapper teamMapper;

    @BeforeEach
    void init() {
        this.teamMapper = new TeamMapper();
        this.teamService = new TeamService(teammateRepository, teamRepository, teamMapper);
    }

    @Test
    @Commit
    void findTeamById() {
        Optional<Team> team = teamRepository.findById(1L);

        assertTrue(team.isPresent());
        assertFalse(TestTransaction.isActive());
    }

    @Test
    @Commit
    void getTeammate() {
        TeammateModel teammate = teamService.getTeammate(1L);

        assertNotNull(teammate);
        assertFalse(TestTransaction.isActive());
    }

    @Test
    @Commit
    void createTeam() {
        String response = teamService.createTeam(createTeamModel());

        assertEquals(response, "Successful created a team, name: " + "test-team-name");
    }

    @Test
    void findAll() {
        List<TeamModel> teamModels = teamService.getAllTeams();

        assertTrue(teamModels.size() > 0);
    }


    private TeamModel createTeamModel() {
        return TeamModel.builder()
                //.id(27L)
                .name("test-team-name")
                .type("test-type")
                .members(Collections.singletonList(createTeammateModel()))
                .build();
    }

    private TeammateModel createTeammateModel() {
        return TeammateModel.builder()
                //.id(5L)
                .name("test-name")
                .teamName("test-team-name")
                .age((byte) 99)
                .lastName("test-last-name")
                .middleName("test-middle-name")
                .position("test-position")
                .build();
    }
}