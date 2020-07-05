package com.example.business;

import com.example.business.model.TeamModel;
import com.example.business.model.TeammateModel;
import com.example.business.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/team")
@RequiredArgsConstructor
public class MainController {

    private final TeamService teamService;

    @GetMapping("/all")
    public List<TeamModel> getAllTeams() {
        return teamService.getAllTeams();
    }

    @PostMapping("/create")
    public String createTeam(@RequestBody TeamModel teamModel) {
        return teamService.createTeam(teamModel);
    }

    @PostMapping("/update")
    public String updateTeam(@RequestBody TeamModel teamModel) {
        return teamService.updateTeam(teamModel);
    }

    @GetMapping("/delete")
    public String deleteTeam(@RequestParam String teamName) {
        return teamService.deleteTeam(teamName);
    }

    @GetMapping("/teammate")
    public TeammateModel getTeammate(@RequestParam Long id) {
        return teamService.getTeammate(id);
    }

    @PostMapping("/teammate/create")
    public String createTeam(@RequestBody TeammateModel teammateModel) {
        return teamService.createTeammate(teammateModel);
    }

    @PostMapping("/teammate/update")
    public String updateTeam(@RequestBody TeammateModel teammateModel) {
        return teamService.updateTeammate(teammateModel);
    }

    @GetMapping("/teammate/delete")
    public String deleteTeammate(@RequestParam Long id) {
        return teamService.deleteTeammate(id);
    }
}
