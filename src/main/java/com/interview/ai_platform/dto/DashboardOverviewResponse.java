package com.interview.ai_platform.dto;

public class DashboardOverviewResponse {

    private long totalAttempts;
    private int totalScore;
    private double averageScore;

    public DashboardOverviewResponse(long totalAttempts, int totalScore, double averageScore) {
        this.totalAttempts = totalAttempts;
        this.totalScore = totalScore;
        this.averageScore = averageScore;
    }

    public long getTotalAttempts() {
        return totalAttempts;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public double getAverageScore() {
        return averageScore;
    }
}