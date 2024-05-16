package com.fantasyhnl.player;

import com.fantasyhnl.util.BaseController;
import com.fantasyhnl.util.BaseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/player")
public class PlayerController extends BaseController<Player, PlayerDto> {
    public PlayerController(BaseService<Player, PlayerDto> baseService) {
        super(baseService);
    }
}
