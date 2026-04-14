package com.interview.ai_platform.dto;

public class RecommendationResponse {

    private String recommendedTopic;
    private String reason;

    public RecommendationResponse(String recommendedTopic, String reason) {
        this.recommendedTopic = recommendedTopic;
        this.reason = reason;
    }

    public String getRecommendedTopic() {
        return recommendedTopic;
    }

    public String getReason() {
        return reason;
    }
}