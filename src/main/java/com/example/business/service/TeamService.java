package com.example.business.service;

import com.example.business.entity.Team;
import com.example.business.entity.Teammate;
import com.example.business.model.TeamModel;
import com.example.business.model.TeammateModel;
import com.example.business.repo.TeamRepository;
import com.example.business.repo.TeammateRepository;
import com.example.business.service.mappers.TeamMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Transactional
@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeammateRepository teammateRepository;
    private final TeamRepository teamRepository;
    private final TeamMapper teamMapper;

    public String createTeam(TeamModel teamModel) {
        setTeamForTeammates(teamModel);
        Team team = makeTeam(teamModel);
        teamRepository.save(team);
        return toTeamNameResponse(team);
    }

    public String createTeammate(TeammateModel teammateModel) {
        Teammate teammate = teamMapper.toTeammate(teammateModel);
        teamRepository.findTeamByName(teammateModel.getTeamName())
                .map(team -> team.getMembers().add(teammate))
                .orElseThrow(() -> new RuntimeException("Team Not Found"));
        teammateRepository.save(teammate);
        return "Teammate with id " + teammate.getTeammateId() +
                " was added to " + teammate.getTeamName() + " successfully";
    }

    public TeammateModel getTeammate(Long id) {
        return teammateRepository.findById(id)
                .map(teamMapper::toTeammateModel)
                .orElseThrow(() -> new RuntimeException("No such teammate found, with id: " + id));
    }

    public List<TeamModel> getAllTeams() {
        return teamRepository.findAll()
                .stream()
                .map(teamMapper::toTeamModel)
                .collect(toList());
    }

    public String deleteTeammate(Long id) {
        teammateRepository.findById(id)
                .ifPresent(this::deleteTeammate);

        return "Teammate with id: " + id + " was successfully deleted";
    }

    public String deleteTeam(String teamName) {
        teamRepository.deleteTeamByName(teamName);
        return "Team: " + teamName + " was successfully deleted";
    }

    private Team makeTeam(TeamModel teamModel) {
        List<Teammate> teammates = teamMapper.toTeammates(teamModel.getMembers());
        Team team = teamMapper.toTeam(teamModel, teammates);
        teammates.stream()
                .peek(teammate -> teammate.setTeamName(team.getName()))
                .forEach(teammateRepository::save);
        return teamRepository.save(team);
    }

    private void deleteTeammate(Teammate teammate) {
        teamRepository.findTeamByName(teammate.getTeamName())
                .map(team -> team.getMembers().remove(teammate));
        teammateRepository.deleteById(teammate.getTeammateId());
    }

    private void setTeamForTeammates(TeamModel teamModel) {
        teamModel.getMembers()
                .forEach(teammate -> teammate.setTeamName(teamModel.getName()));
    }

    private String toTeamNameResponse(Team team) {
        return "Successful created a team, name: " + team.getName();
    }
}
