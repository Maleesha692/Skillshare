package com.example.demo.controller;

import com.example.demo.model.LearningPlan;
import com.example.demo.service.LearningPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/learning-plans")
@CrossOrigin(origins = "*")  // Allow requests from any origin (for development)
public class LearningPlanController {

    private final LearningPlanService learningPlanService;

    @Autowired
    public LearningPlanController(LearningPlanService learningPlanService) {
        this.learningPlanService = learningPlanService;
    }

    // Create a new learning plan
    @PostMapping
    public ResponseEntity<LearningPlan> createLearningPlan(@RequestBody LearningPlan learningPlan) {
        LearningPlan createdLearningPlan = learningPlanService.createLearningPlan(learningPlan);
        return new ResponseEntity<>(createdLearningPlan, HttpStatus.CREATED);
    }

    // Get all learning plans
    @GetMapping
    public ResponseEntity<List<LearningPlan>> getAllLearningPlans() {
        List<LearningPlan> learningPlans = learningPlanService.getAllLearningPlans();
        return new ResponseEntity<>(learningPlans, HttpStatus.OK);
    }

    // Get a learning plan by ID
    @GetMapping("/{id}")
    public ResponseEntity<LearningPlan> getLearningPlanById(@PathVariable Long id) {
        Optional<LearningPlan> learningPlan = learningPlanService.getLearningPlanById(id);
        
        return learningPlan.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Update a learning plan
    @PutMapping("/{id}")
    public ResponseEntity<LearningPlan> updateLearningPlan(@PathVariable Long id, @RequestBody LearningPlan learningPlan) {
        LearningPlan updatedLearningPlan = learningPlanService.updateLearningPlan(id, learningPlan);
        
        if (updatedLearningPlan != null) {
            return new ResponseEntity<>(updatedLearningPlan, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete a learning plan
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteLearningPlan(@PathVariable Long id) {
        try {
            learningPlanService.deleteLearningPlan(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
