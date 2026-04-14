package com.interview.ai_platform.dto;

public class TotalScoreResponse {

    private int totalScore;

    public TotalScoreResponse(int totalScore) {
        this.totalScore = totalScore;
    }

    public int getTotalScore() {
        return totalScore;
    }
}