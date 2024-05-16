package com.fantasyhnl.fixture;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        return ResponseEntity.status(HttpStatus.CREATED).body("Resources created");
    }
}
