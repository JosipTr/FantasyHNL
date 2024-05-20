package com.fantasyhnl.player;

import com.fantasyhnl.team.TeamRepository;
import com.fantasyhnl.util.BaseRepository;
import com.fantasyhnl.util.BaseService;
import com.fantasyhnl.util.JsonToObjectMapper;
import com.fantasyhnl.util.RestService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import static com.fantasyhnl.util.Constants.*;

@Service
public class PlayerService extends BaseService<Player, PlayerDto> {

    private final TeamRepository teamRepository;

    protected PlayerService(RestService restService, JsonToObjectMapper objectMapper, ModelMapper modelMapper, BaseRepository<Player> baseRepository, TeamRepository teamRepository) {
        super(restService, objectMapper, modelMapper, baseRepository);
        this.teamRepository = teamRepository;
    }

    @Override
    @Transactional
    public void add() {
        var teams = teamRepository.findAll();
        for (var team : teams) {
            var url = basicPlayerPath + team.getId() + ".json";
            var response = getRootResponse(url, BasicPlayerResponse.class);
            for (var res : response) {
                var players = res.getPlayers();
                for (var player : players) {
                    player.setTeam(team);
                    team.addPlayer(player);
                    baseRepository.save(player);
                }
            }
        }
        var players = baseRepository.findAll();
        for (var player : players) {
            for (var i = 1; i <= 28; i++) {
                var url = playerPath + i + ".json";
                var response = getRootResponse(url, PlayerResponse.class);
                for (var res : response) {
                    if (player.getId() == res.getPlayer().getId()) {
                        player.updatePlayer(res.getPlayer());
                    }
                }
            }
        }
    }

    @Override
    public void update() {}

    @Override
    protected Class<PlayerDto> getDtoClass() {
        return PlayerDto.class;
    }
}
