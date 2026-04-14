package com.interview.ai_platform.dto;

public class AverageScoreResponse {

    private double averageScore;

    public AverageScoreResponse(double averageScore) {
        this.averageScore = averageScore;
    }

    public double getAverageScore() {
        return averageScore;
    }
}