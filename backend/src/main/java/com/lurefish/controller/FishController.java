package com.lurefish.controller;

import com.lurefish.model.Fish;
import com.lurefish.service.FishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fish")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:8080"})
public class FishController {

    private final FishService fishService;

    @Autowired
    public FishController(FishService fishService) {
        this.fishService = fishService;
    }

    @GetMapping
    public ResponseEntity<List<Fish>> getAllFish() {
        List<Fish> fishList = fishService.getAllFish();
        return ResponseEntity.ok(fishList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Fish> getFishById(@PathVariable Long id) {
        Fish fish = fishService.getFishById(id);
        return ResponseEntity.ok(fish);
    }

    @PostMapping
    public ResponseEntity<Fish> createFish(@RequestBody Fish fish) {
        Fish createdFish = fishService.createFish(fish);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFish);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Fish> updateFish(@PathVariable Long id, @RequestBody Fish fish) {
        Fish updatedFish = fishService.updateFish(id, fish);
        return ResponseEntity.ok(updatedFish);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFish(@PathVariable Long id) {
        fishService.deleteFish(id);
        return ResponseEntity.noContent().build();
    }
}