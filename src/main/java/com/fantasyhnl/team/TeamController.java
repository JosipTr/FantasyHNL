package com.fantasyhnl.team;

import com.fantasyhnl.util.BaseController;
import com.fantasyhnl.util.BaseService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/team")
public class TeamController extends BaseController<Team, TeamDto> {

    public TeamController(BaseService<Team, TeamDto> baseService) {
        super(baseService);
    }
}
