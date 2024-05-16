package com.fantasyhnl.fixture;

import com.fantasyhnl.util.BaseController;
import com.fantasyhnl.util.BaseService;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/fixture")
public class FixtureController extends BaseController<Fixture, FixtureDto> {

    public FixtureController(BaseService<Fixture, FixtureDto> baseService) {
        super(baseService);
    }

//    @GetMapping
//    public ResponseEntity<List<FixtureDto>> getFixtures() {
//        var fixtures = fixtureService.getFixtures();
//        return ResponseEntity.ok(fixtures);
//    }
//
//    @PostMapping
//    public ResponseEntity<String> addFixtures() {
//        fixtureService.addFixtures();
//        return ResponseEntity.status(HttpStatus.CREATED).body("Resources created");
//    }
//
//    @PutMapping
//    public ResponseEntity<String> updateFixtures() {
//        fixtureService.updateFixtures();
//        return ResponseEntity.ok("Resources updated");
//    }
}
