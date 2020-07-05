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

    private static final String NO_TEAM_ERROR = "Team Not Found.";

    private final TeammateRepository teammateRepository;
    private final TeamRepository teamRepository;
    private final TeamMapper teamMapper;

    public String createTeam(TeamModel teamModel) {
        setTeamForTeammates(teamModel);
        Team team = makeTeam(teamModel);
        teamRepository.save(team);
        return toTeamNameResponse(team);
    }

    public String updateTeam(TeamModel teamModel) {

        if (teamModel.getId() == null) throw new RuntimeException("What about team id?");

        setTeamForTeammates(teamModel);
        teamRepository.findById(teamModel.getId())
                .map(team -> refreshTeamFields(teamModel, team))
                .orElseThrow(() -> new RuntimeException(NO_TEAM_ERROR));

        return "Team " + teamModel.getName() + " was successfully updated.";
    }

    public String createTeammate(TeammateModel teammateModel) {
        Teammate teammate = teamMapper.toTeammate(teammateModel);
        teamRepository.findTeamByName(teammateModel.getTeamName())
                .map(team -> team.getMembers().add(teammate))
                .orElseThrow(() -> new RuntimeException(NO_TEAM_ERROR));
        teammateRepository.save(teammate);
        return "Teammate with id " + teammate.getId() +
                " was added to " + teammate.getTeamName() + " successfully";
    }

    public String updateTeammate(TeammateModel teammateModel) {

        if (teammateModel.getId() == null) throw new RuntimeException("What about teammate id?");

        Teammate updatedTeammate = teamMapper.toTeammate(teammateModel);
        Teammate teammateToUpdate = teammateRepository.findById(teammateModel.getId())
                .orElseThrow(() -> new RuntimeException("Teammate not found id: " + teammateModel.getId()));

        if (!teammateModel.getTeamName().equals(teammateToUpdate.getTeamName())) {
            removeTeammateFromOldTeam(teammateToUpdate);
            addToNewTeam(teammateToUpdate, teammateModel.getTeamName());
        }

        teammateRepository.save(updatedTeammate);
        return "Teammate was updated";
    }

    public TeammateModel getTeammate(Long id) {
        return teammateRepository.findById(id)
                .map(teamMapper::toTeammateModel)
                .orElseThrow(() -> new RuntimeException("Teammate not found id: " + id));
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
        teammates.forEach(teammateRepository::save);
        return teamRepository.save(team);
    }

    private void deleteTeammate(Teammate teammate) {
        teamRepository.findTeamByName(teammate.getTeamName())
                .map(team -> team.getMembers().remove(teammate));
        teammateRepository.deleteById(teammate.getId());
    }

    private void setTeamForTeammates(TeamModel teamModel) {
        teamModel.getMembers()
                .forEach(teammate -> teammate.setTeamName(teamModel.getName()));
    }

    private String toTeamNameResponse(Team team) {
        return "Successful created a team, name: " + team.getName();
    }

    private Team refreshTeamFields(TeamModel teamModel, Team team) {
        team.setName(teamModel.getName());
        team.setType(teamModel.getType());
        team.getMembers().forEach(x -> x.setTeamName(teamModel.getName()));
        return teamRepository.save(team);
    }

    private void removeTeammateFromOldTeam(Teammate teammateToUpdate) {
        teamRepository.findTeamByName(teammateToUpdate.getTeamName())
                .map(team -> team.getMembers().removeIf(teammate -> teammate.getId().equals(teammateToUpdate.getId())));
    }

    private void addToNewTeam(Teammate teammateToUpdate, String teamName) {
        teamRepository.findTeamByName(teamName)
                .map(team1 -> team1.getMembers().add(teammateToUpdate))
                .orElseThrow(() -> new RuntimeException("no such team"));
    }
}
