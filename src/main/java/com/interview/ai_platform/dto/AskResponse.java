package com.interview.ai_platform.dto;

public class AskResponse {

    private String answer;
    private int score;
    private String feedback;

    public AskResponse(String answer, int score, String feedback) {
        this.answer = answer;
        this.score = score;
        this.feedback = feedback;
    }

    public String getAnswer() {
        return answer;
    }

    public int getScore() {
        return score;
    }

    public String getFeedback() {
        return feedback;
    }
}