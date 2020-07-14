package com.example.business.service.mappers;

import com.example.business.entity.Team;
import com.example.business.entity.Teammate;
import com.example.business.model.TeamModel;
import com.example.business.model.TeammateModel;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Component is intended to map
 * response/request models {@link TeamModel}, {@link TeammateModel}
 * to entities {@link Team},{@link Teammate}  and backward
 */
@Component
public class TeamMapper {

    public Team toTeam(TeamModel teamModel) {
        return Team.builder()
                .id(teamModel.getId())
                .name(teamModel.getName())
                .type(teamModel.getType())
                .build();
    }

    public List<Teammate> toTeammates(List<TeammateModel> members) {
        return members.stream()
                .map(this::toTeammate)
                .collect(toList());
    }

    public Teammate toTeammate(TeammateModel teammateModel) {
        return Teammate.builder()
                .id(teammateModel.getId())
                .age(teammateModel.getAge())
                .lastName(teammateModel.getLastName())
                .middleName(teammateModel.getMiddleName())
                .position(teammateModel.getPosition())
                .teammateName(teammateModel.getName())
                .teamName(teammateModel.getTeamName())
                .build();
    }

    public TeamModel toTeamModel(Team team, List<Teammate> teammates) {
        return TeamModel.builder()
                .id(team.getId())
                .name(team.getName())
                .type(team.getType())
                .members(toTeammateModel(teammates))
                .build();
    }

    public List<TeammateModel> toTeammateModel(List<Teammate> teammates) {
        return teammates.stream()
                .map(this::toTeammateModel)
                .collect(toList());
    }

    public TeammateModel toTeammateModel(Teammate teammate) {
        return TeammateModel.builder()
                .id(teammate.getId())
                .age(teammate.getAge())
                .name(teammate.getTeammateName())
                .lastName(teammate.getLastName())
                .middleName(teammate.getMiddleName())
                .position(teammate.getPosition())
                .teamName(teammate.getTeamName())
                .build();
    }
}
