package com.fantasyhnl.team;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/team")
public class TeamController {
    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }
    @GetMapping
    public ResponseEntity<List<Team>> getTeams() {
        var teams = teamService.getTeams();
        return ResponseEntity.ok(teams);
    }
    @PostMapping
    public ResponseEntity<String> addTeams() {
        teamService.addTeams();
        return ResponseEntity.status(HttpStatus.CREATED).body("Resource created successfully");
    }
}
