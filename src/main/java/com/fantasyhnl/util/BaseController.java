package com.fantasyhnl.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public class BaseController<T, D> {
    private final BaseService<T, D> baseService;

    public BaseController(BaseService<T, D> baseService) {
        this.baseService = baseService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<D> getById(@PathVariable int id) {
        var result = baseService.getById(id);
        return ResponseEntity.ok(result);
    }

    @GetMapping
    public ResponseEntity<List<D>> getAll() {
        var result = baseService.getAll();
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<String> add() {
        baseService.add();
        return ResponseEntity.status(HttpStatus.CREATED).body("Resources created");
    }

    @DeleteMapping
    public ResponseEntity<String> delete() {
        baseService.delete();
        return ResponseEntity.ok("Resources deleted");
    }

}
