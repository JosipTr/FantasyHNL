package com.fantasyhnl.player;

import com.fantasyhnl.team.TeamRepository;
import com.fantasyhnl.util.BaseRepository;
import com.fantasyhnl.util.BaseService;
import com.fantasyhnl.util.JsonToObjectMapper;
import com.fantasyhnl.util.RestService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

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
            response.forEach(res -> {
                res.getPlayers().forEach(player -> {
                    player.setTeam(team);
                    team.addPlayer(player);
                    baseRepository.save(player);
                });
            });
        }
        updatePlayerInfo(1, 0);
    }

    private void updatePlayerInfo(int current, int total) {
        System.out.println(current);
        var url = playerPath + current + ".json";
        var root = getRoot(url, PlayerResponse.class);
        var response = root.getResponse();
        total = root.getPaging().getTotal();
        current = root.getPaging().getCurrent() + 1;
        for (var res : response) {
            var player = res.getPlayer();
            baseRepository.findById(player.getId()).ifPresent(savedPlayer -> {
                savedPlayer.setFirstname(player.getFirstname());
                savedPlayer.setLastname(player.getLastname());
                savedPlayer.setInjured(player.getInjured());
            });
        }
        if (current > total) return;
        updatePlayerInfo(current, total);
    }

    @Override
    public void update() {
    }

    @Override
    protected Class<PlayerDto> getDtoClass() {
        return PlayerDto.class;
    }
}
