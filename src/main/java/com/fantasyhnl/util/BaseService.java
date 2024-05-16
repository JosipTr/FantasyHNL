package com.fantasyhnl.util;

import com.fantasyhnl.exception.EmptyListException;
import com.fantasyhnl.exception.InvalidIdException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.fantasyhnl.util.Constants.emptyList;

@Service
public abstract class BaseService<T, D> {
    protected final RestService restService;
    protected final JsonToObjectMapper objectMapper;
    protected final ModelMapper modelMapper;
    protected final BaseRepository<T> baseRepository;

    protected BaseService(RestService restService, JsonToObjectMapper objectMapper, ModelMapper modelMapper, BaseRepository<T> baseRepository) {
        this.restService = restService;
        this.objectMapper = objectMapper;
        this.modelMapper = modelMapper;
        this.baseRepository = baseRepository;
    }

    protected D getById(int id) {
        var result = baseRepository.findById(id);
        if (result.isPresent()) return convertToDto(result.get());
        throw new InvalidIdException("Invalid ID");
    }

    protected List<D> getAll() {
        var results = baseRepository.findAll();
        if (results.isEmpty()) throw new EmptyListException(emptyList);
        return results.stream().map(this::convertToDto).toList();
    }

    protected void delete() {
        baseRepository.deleteAll();
    }

    public abstract void add();

    public abstract void update();

    protected void updateById(int id) {

    }

    protected void waitSeconds(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    protected void writeToFile(String body, String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(body);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected String readFromFile(String fileName) {
        try {
            return Files.readString(Path.of(fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected D convertToDto(T entity) {
        return modelMapper.map(entity, getDtoClass());
    }

    protected abstract Class<D> getDtoClass();
}
