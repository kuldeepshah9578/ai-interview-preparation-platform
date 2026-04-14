package com.interview.ai_platform.dto;

public class TopicLeaderboardResponse {

    private String topic;
    private double averageScore;

    public TopicLeaderboardResponse(String topic, double averageScore) {
        this.topic = topic;
        this.averageScore = averageScore;
    }

    public String getTopic() {
        return topic;
    }

    public double getAverageScore() {
        return averageScore;
    }
}