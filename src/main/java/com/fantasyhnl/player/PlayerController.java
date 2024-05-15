package com.fantasyhnl.player;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping
    public ResponseEntity<String> updatePlayers() {
        playerService.updatePlayers();
        return ResponseEntity.ok("Resource updated successfully");
    }
}
