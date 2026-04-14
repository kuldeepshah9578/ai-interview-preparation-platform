package com.interview.ai_platform.dto;

public class DashboardPerformanceResponse {

    private String topTopic;
    private String recommendedTopic;

    public DashboardPerformanceResponse(String topTopic, String recommendedTopic) {
        this.topTopic = topTopic;
        this.recommendedTopic = recommendedTopic;
    }

    public String getTopTopic() {
        return topTopic;
    }

    public String getRecommendedTopic() {
        return recommendedTopic;
    }
}