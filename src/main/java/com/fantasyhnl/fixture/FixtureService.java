package com.fantasyhnl.fixture;

import com.fantasyhnl.util.JsonToObjectMapper;
import com.fantasyhnl.util.RestService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class FixtureService {
    private final FixtureRepository fixtureRepository;
    private final JsonToObjectMapper objectMapper;
    private final ModelMapper modelMapper;
    private final RestService restService;

    public FixtureService(FixtureRepository fixtureRepository, JsonToObjectMapper objectMapper, ModelMapper modelMapper, RestService restService) {
        this.fixtureRepository = fixtureRepository;
        this.objectMapper = objectMapper;
        this.modelMapper = modelMapper;
        this.restService = restService;
    }

    public List<Fixture> getFixtures() {
        return fixtureRepository.findAll();
    }

    public void addFixtures() {
        var body = readFromFile();
        var root = objectMapper.mapToRootObject(body, FixtureResponse.class);
        var response = root.getResponse();
        for (var res : response) {
            var fixture = res.getFixture();
            var status = fixture.getStatus();
            status.setFixture(fixture);
            fixture.setStatus(status);
            fixtureRepository.save(fixture);
        }
    }

    private void waitSeconds(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void writeToFile(String body, int id) {
        try (FileWriter writer = new FileWriter("./src/main/resources/data/fixture/fixture.json")) {
            writer.write(body);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void writeToOtherFile(String body, int id) {
        try (FileWriter writer = new FileWriter("./src/main/resources/data/players/players" + id + ".json")) {
            writer.write(body);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String readFromFile() {
        try {
            return Files.readString(Path.of("./src/main/resources/data/fixture/fixture.json"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String readFromOtherFile(int id) {
        try {
            return Files.readString(Path.of("./src/main/resources/data/players/players" + id + ".json"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
