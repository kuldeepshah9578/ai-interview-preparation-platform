package com.interview.ai_platform.dto;

public class TopTopicResponse {

    private String topic;
    private int averageScore;

    public TopTopicResponse(String topic, int averageScore) {
        this.topic = topic;
        this.averageScore = averageScore;
    }

    public String getTopic() {
        return topic;
    }

    public int getAverageScore() {
        return averageScore;
    }
}