package com.fantasyhnl.player;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/player")
public class PlayerController {
    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping
    public ResponseEntity<List<PlayerDto>> getPlayers() {
        var players = playerService.getPlayers();
        return ResponseEntity.ok(players);
    }

    @PostMapping
    public ResponseEntity<String> addPlayers() {
        playerService.addPlayers();
        return ResponseEntity.ok("Resource created successfully");
    }
}
