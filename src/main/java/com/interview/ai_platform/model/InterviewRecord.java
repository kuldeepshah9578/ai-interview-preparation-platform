package com.interview.ai_platform.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class InterviewRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String question;
    private String category;
    private String difficulty;
    private int score;
    private LocalDateTime createdAt;

    public InterviewRecord() {
    }

    public InterviewRecord(String question, String category, String difficulty, int score) {
        this.question = question;
        this.category = category;
        this.difficulty = difficulty;
        this.score = score;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public String getCategory() {
        return category;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public int getScore() {
        return score;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }
}