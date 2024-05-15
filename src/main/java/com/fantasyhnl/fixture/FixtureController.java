package com.fantasyhnl.fixture;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fixture")
public class FixtureController {
    private final FixtureService fixtureService;

    public FixtureController(FixtureService fixtureService) {
        this.fixtureService = fixtureService;
    }

    @GetMapping
    public ResponseEntity<List<Fixture>> getFixtures() {
        var fixtures = fixtureService.getFixtures();
        return ResponseEntity.ok(fixtures);
    }

    @PostMapping
    public ResponseEntity<String> addFixtures() {
        fixtureService.addFixtures();
        return ResponseEntity.status(HttpStatus.CREATED).body("Resources created successfully");
    }

    @DeleteMapping
    public ResponseEntity<String> deleteFixtures() {
        fixtureService.deleteFixtures();
        return ResponseEntity.ok("Resources deleted");
    }
}
