package com.interview.ai_platform.dto;

public class SummaryResponse {

    private int totalQuestions;
    private int totalCategories;
    private String weakestTopic;
    private String recommendedTopic;

    public SummaryResponse(int totalQuestions, int totalCategories, String weakestTopic, String recommendedTopic) {
        this.totalQuestions = totalQuestions;
        this.totalCategories = totalCategories;
        this.weakestTopic = weakestTopic;
        this.recommendedTopic = recommendedTopic;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public int getTotalCategories() {
        return totalCategories;
    }

    public String getWeakestTopic() {
        return weakestTopic;
    }

    public String getRecommendedTopic() {
        return recommendedTopic;
    }
}