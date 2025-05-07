package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class LearningProgress {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private int learningPlanId;
    
    private String title;
    
    private String description;
    
    private String startDate;
    
    private String endDate;
    
    private String status;
    
    private int completionPercentage;
    
    // Default constructor
    public LearningProgress() {
    }
    
    // Constructor with all fields
    public LearningProgress(Long id, int learningPlanId, String title, String description,
                         String startDate, String endDate, String status, int completionPercentage) {
        this.id = id;
        this.learningPlanId = learningPlanId;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.completionPercentage = completionPercentage;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getLearningPlanId() {
        return learningPlanId;
    }

    public void setLearningPlanId(int learningPlanId) {
        this.learningPlanId = learningPlanId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCompletionPercentage() {
        return completionPercentage;
    }

    public void setCompletionPercentage(int completionPercentage) {
        this.completionPercentage = completionPercentage;
    }
}
