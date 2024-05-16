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
}
