package com.example.demo.controller;

import com.example.demo.model.LearningProgress;
import com.example.demo.service.LearningProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/learning-progress")
@CrossOrigin(origins = "*")  // Allow requests from any origin (for development)
public class LearningProgressController {

    private final LearningProgressService learningProgressService;

    @Autowired
    public LearningProgressController(LearningProgressService learningProgressService) {
        this.learningProgressService = learningProgressService;
    }

    // Create a new learning progress
    @PostMapping
    public ResponseEntity<LearningProgress> createLearningProgress(@RequestBody LearningProgress learningProgress) {
        LearningProgress createdLearningProgress = learningProgressService.createLearningProgress(learningProgress);
        return new ResponseEntity<>(createdLearningProgress, HttpStatus.CREATED);
    }

    // Get all learning progress entries
    @GetMapping
    public ResponseEntity<List<LearningProgress>> getAllLearningProgress() {
        List<LearningProgress> learningProgressList = learningProgressService.getAllLearningProgress();
        return new ResponseEntity<>(learningProgressList, HttpStatus.OK);
    }

    // Get a learning progress by ID
    @GetMapping("/{id}")
    public ResponseEntity<LearningProgress> getLearningProgressById(@PathVariable Long id) {
        Optional<LearningProgress> learningProgress = learningProgressService.getLearningProgressById(id);
        
        return learningProgress.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Update a learning progress
    @PutMapping("/{id}")
    public ResponseEntity<LearningProgress> updateLearningProgress(@PathVariable Long id, @RequestBody LearningProgress learningProgress) {
        LearningProgress updatedLearningProgress = learningProgressService.updateLearningProgress(id, learningProgress);
        
        if (updatedLearningProgress != null) {
            return new ResponseEntity<>(updatedLearningProgress, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete a learning progress
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteLearningProgress(@PathVariable Long id) {
        try {
            learningProgressService.deleteLearningProgress(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
