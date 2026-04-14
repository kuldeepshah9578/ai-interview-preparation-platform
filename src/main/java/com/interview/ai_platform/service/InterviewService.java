package com.interview.ai_platform.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.interview.ai_platform.dto.AverageScoreResponse;
import com.interview.ai_platform.dto.DashboardOverviewResponse;
import com.interview.ai_platform.dto.DashboardPerformanceResponse;
import com.interview.ai_platform.dto.DashboardStatusResponse;
import com.interview.ai_platform.dto.EvaluationResult;
import com.interview.ai_platform.dto.RecommendationResponse;
import com.interview.ai_platform.dto.SummaryResponse;
import com.interview.ai_platform.dto.TopTopicResponse;
import com.interview.ai_platform.dto.TopicLeaderboardResponse;
import com.interview.ai_platform.dto.TotalAttemptsResponse;
import com.interview.ai_platform.dto.TotalScoreResponse;
import com.interview.ai_platform.exception.ResourceNotFoundException;
import com.interview.ai_platform.model.InterviewRecord;
import com.interview.ai_platform.repository.InterviewRecordRepository;
@Service
public class InterviewService {

    private final InterviewRecordRepository interviewRecordRepository;

    public InterviewService(InterviewRecordRepository interviewRecordRepository) {
        this.interviewRecordRepository = interviewRecordRepository;
    }

    public EvaluationResult getAnswer(String question, String category, String difficulty) {

        String lowerQuestion = question == null ? "" : question.toLowerCase();
        String lowerCategory = category == null ? "" : category.toLowerCase();
        String lowerDifficulty = difficulty == null ? "" : difficulty.toLowerCase();

        String answer;
        int score;
        String feedback;

        if (lowerCategory.contains("java")) {
            if (lowerDifficulty.contains("easy")) {
                answer = "Java is a high-level, object-oriented programming language used for backend development, Android apps, and enterprise systems.";
                score = 7;
                feedback = "Good beginner-level answer. Add JVM and platform independence for a better response.";
            } else if (lowerDifficulty.contains("medium")) {
                answer = "Java is a class-based, object-oriented language known for platform independence through the JVM. It is widely used in backend systems and enterprise applications.";
                score = 8;
                feedback = "Solid medium-level answer. Mention JDK, JRE, and memory management to improve further.";
            } else if (lowerDifficulty.contains("hard")) {
                answer = "Java is a high-level, object-oriented language that achieves platform independence via JVM bytecode execution. It is widely used for scalable backend systems, enterprise applications, and distributed architectures.";
                score = 9;
                feedback = "Strong advanced answer. You can make it even better by discussing garbage collection, multithreading, and JVM internals.";
            } else {
                answer = "Java is a popular object-oriented programming language used in backend development.";
                score = 7;
                feedback = "Specify a difficulty level like Easy, Medium, or Hard for better evaluation.";
            }
        }
        else if (lowerCategory.contains("spring")) {
            if (lowerDifficulty.contains("easy")) {
                answer = "Spring Boot is a framework that makes Java backend development easier by reducing configuration.";
                score = 7;
                feedback = "Good start. Add embedded server and auto-configuration concepts.";
            } else if (lowerDifficulty.contains("medium")) {
                answer = "Spring Boot simplifies backend development by offering auto-configuration, embedded servers, and production-ready features for Java applications.";
                score = 8;
                feedback = "Good medium-level answer. Mention REST APIs and dependency injection.";
            } else if (lowerDifficulty.contains("hard")) {
                answer = "Spring Boot accelerates Java backend development through convention-over-configuration, embedded servers, auto-configuration, and seamless integration with production-ready tools for scalable microservices.";
                score = 9;
                feedback = "Very strong answer. Add actuator, profiles, and microservice architecture to sound even better.";
            } else {
                answer = "Spring Boot is a framework used for Java backend development.";
                score = 7;
                feedback = "Specify a difficulty level for better evaluation.";
            }
        }
        else if (lowerCategory.contains("oops")) {
            answer = "OOPS includes encapsulation, inheritance, polymorphism, and abstraction.";
            score = 8;
            feedback = "Explain each principle with an example to strengthen your answer.";
        }
        else if (lowerCategory.contains("sql")) {
            answer = "SQL is used to store, retrieve, update, and manage data in relational databases.";
            score = 7;
            feedback = "Add joins, normalization, and indexing for a stronger answer.";
        }
        else if (lowerQuestion.contains("java")) {
            answer = "Java is a widely used programming language for backend development.";
            score = 7;
            feedback = "Category and difficulty would help generate a more precise answer.";
        }
        else {
            answer = "This is a good interview question. I am improving the answer engine with category and difficulty awareness.";
            score = 5;
            feedback = "Try sending category and difficulty to get a more accurate interview-style response.";
        }

        interviewRecordRepository.save(new InterviewRecord(question, category, difficulty, score));

        return new EvaluationResult(answer, score, feedback);
    }

    public List<InterviewRecord> getHistory() {
        return interviewRecordRepository.findAll();
    }
    public List<InterviewRecord> getRecentHistory() {
        return interviewRecordRepository.findAllByOrderByCreatedAtDesc();
    }
    
    public List<InterviewRecord> getHistoryByCategory(String category) {
        return interviewRecordRepository.findByCategoryIgnoreCase(category);
    }

    public Map<String, Integer> getCategoryAnalytics() {
        Map<String, Integer> analytics = new HashMap<>();

        for (InterviewRecord record : interviewRecordRepository.findAll()) {
            String category = record.getCategory();

            if (category == null || category.isBlank()) {
                category = "Unknown";
            }

            analytics.put(category, analytics.getOrDefault(category, 0) + 1);
        }

        return analytics;
    }

    public Map<String, Integer> getWeakAreas() {
        Map<String, Integer> totalScores = new HashMap<>();
        Map<String, Integer> counts = new HashMap<>();
        Map<String, Integer> weakAreas = new HashMap<>();

        for (InterviewRecord record : interviewRecordRepository.findAll()) {
            String category = record.getCategory();

            if (category == null || category.isBlank()) {
                category = "Unknown";
            }

            totalScores.put(category, totalScores.getOrDefault(category, 0) + record.getScore());
            counts.put(category, counts.getOrDefault(category, 0) + 1);
        }

        for (String category : totalScores.keySet()) {
            int average = totalScores.get(category) / counts.get(category);

            if (average <= 7) {
                weakAreas.put(category, average);
            }
        }

        return weakAreas;
    }

    public RecommendationResponse getRecommendation() {
        Map<String, Integer> weakAreas = getWeakAreas();

        if (weakAreas.isEmpty()) {
            return new RecommendationResponse(
                    "No weak area found",
                    "You are performing well across the practiced topics."
            );
        }

        String weakestTopic = null;
        int lowestScore = Integer.MAX_VALUE;

        for (Map.Entry<String, Integer> entry : weakAreas.entrySet()) {
            if (entry.getValue() < lowestScore) {
                lowestScore = entry.getValue();
                weakestTopic = entry.getKey();
            }
        }

        return new RecommendationResponse(
                weakestTopic,
                "Your average score is lower in this topic. Practice more questions here."
        );
    }

    public SummaryResponse getSummary() {
        List<InterviewRecord> history = interviewRecordRepository.findAll();
        int totalQuestions = history.size();

        Set<String> categories = new HashSet<>();
        for (InterviewRecord record : history) {
            String category = record.getCategory();
            if (category != null && !category.isBlank()) {
                categories.add(category);
            }
        }

        Map<String, Integer> weakAreas = getWeakAreas();

        String weakestTopic = "None";
        int lowestScore = Integer.MAX_VALUE;

        for (Map.Entry<String, Integer> entry : weakAreas.entrySet()) {
            if (entry.getValue() < lowestScore) {
                lowestScore = entry.getValue();
                weakestTopic = entry.getKey();
            }
        }

        String recommendedTopic = getRecommendation().getRecommendedTopic();

        return new SummaryResponse(
                totalQuestions,
                categories.size(),
                weakestTopic,
                recommendedTopic
        );
    }
    public void deleteAllHistory() {
        interviewRecordRepository.deleteAll();
    }
    
    public List<InterviewRecord> getHistoryByDifficulty(String difficulty) {
        return interviewRecordRepository.findByDifficultyIgnoreCase(difficulty);
    }
    public TopTopicResponse getTopTopic() {
        Map<String, Integer> totalScores = new HashMap<>();
        Map<String, Integer> counts = new HashMap<>();

        for (InterviewRecord record : interviewRecordRepository.findAll()) {
            String category = record.getCategory();

            if (category == null || category.isBlank()) {
                category = "Unknown";
            }

            totalScores.put(category, totalScores.getOrDefault(category, 0) + record.getScore());
            counts.put(category, counts.getOrDefault(category, 0) + 1);
        }

        String topTopic = "None";
        int bestAverage = 0;

        for (String category : totalScores.keySet()) {
            int average = totalScores.get(category) / counts.get(category);

            if (average > bestAverage) {
                bestAverage = average;
                topTopic = category;
            }
        }

        return new TopTopicResponse(topTopic, bestAverage);
    }
    public Map<String, Integer> getDifficultyStats() {
        Map<String, Integer> stats = new HashMap<>();

        for (InterviewRecord record : interviewRecordRepository.findAll()) {
            String difficulty = record.getDifficulty();

            if (difficulty == null || difficulty.isBlank()) {
                difficulty = "Unknown";
            }

            stats.put(difficulty, stats.getOrDefault(difficulty, 0) + 1);
        }

        return stats;
    }
    public List<InterviewRecord> searchHistoryByKeyword(String keyword) {
        return interviewRecordRepository.findByQuestionContainingIgnoreCase(keyword);
    }
    public InterviewRecord updateDifficulty(Long id, String difficulty) {
        InterviewRecord record = interviewRecordRepository.findById(id)
        		.orElseThrow(() -> new ResourceNotFoundException("Interview record not found with id: " + id));

        record.setDifficulty(difficulty);
        return interviewRecordRepository.save(record);
    }
    public String deleteHistoryById(Long id) {
        if (!interviewRecordRepository.existsById(id)) {
            throw new RuntimeException("Interview record not found with id: " + id);
        }

        interviewRecordRepository.deleteById(id);
        return "Interview record deleted successfully with id: " + id;
    }
    public Page<InterviewRecord> getPagedHistory(int page, int size) {
        return interviewRecordRepository.findAll(PageRequest.of(page, size));
    }
    public List<InterviewRecord> getSortedHistory(String field, String direction) {

        Sort sort;

        if (direction.equalsIgnoreCase("desc")) {
            sort = Sort.by(field).descending();
        } else {
            sort = Sort.by(field).ascending();
        }

        return interviewRecordRepository.findAll(sort);
    }
    
    public List<InterviewRecord> getHistoryByDateRange(
            LocalDateTime start,
            LocalDateTime end) {

        return interviewRecordRepository.findByCreatedAtBetween(start, end);
    }
    public String exportHistoryToCSV() {

        List<InterviewRecord> records =
                interviewRecordRepository.findAll();

        StringBuilder csv =
                new StringBuilder();

        csv.append(
            "ID,Question,Category,Difficulty,Score,CreatedAt\n"
        );

        for (InterviewRecord record : records) {

            csv.append(record.getId()).append(",")
               .append(record.getQuestion()).append(",")
               .append(record.getCategory()).append(",")
               .append(record.getDifficulty()).append(",")
               .append(record.getScore()).append(",")
               .append(record.getCreatedAt())
               .append("\n");
        }

        return csv.toString();
    }
    public TotalScoreResponse getTotalScore() {
        int total = 0;

        for (InterviewRecord record : interviewRecordRepository.findAll()) {
            total += record.getScore();
        }

        return new TotalScoreResponse(total);
    }
    public AverageScoreResponse getAverageScore() {
        var records = interviewRecordRepository.findAll();

        if (records.isEmpty()) {
            return new AverageScoreResponse(0.0);
        }

        int total = 0;

        for (InterviewRecord record : records) {
            total += record.getScore();
        }

        double average = (double) total / records.size();

        return new AverageScoreResponse(average);
    }
    
    public TotalAttemptsResponse getTotalAttempts() {
        long count = interviewRecordRepository.count();
        return new TotalAttemptsResponse(count);
    }
    public String resetDatabase() {
        interviewRecordRepository.deleteAll();
        return "Database reset successful.";
    }
    public DashboardOverviewResponse getDashboardOverview() {
        long totalAttempts = interviewRecordRepository.count();

        int totalScore = 0;
        var records = interviewRecordRepository.findAll();

        for (InterviewRecord record : records) {
            totalScore += record.getScore();
        }

        double averageScore = records.isEmpty() ? 0.0 : (double) totalScore / records.size();

        return new DashboardOverviewResponse(totalAttempts, totalScore, averageScore);
    }

    public DashboardPerformanceResponse getDashboardPerformance() {
        String topTopic = getTopTopic().getTopic();
        String recommendedTopic = getRecommendation().getRecommendedTopic();

        return new DashboardPerformanceResponse(topTopic, recommendedTopic);
    }

    public DashboardStatusResponse getDashboardStatus() {
        return new DashboardStatusResponse("AI Interview Platform", "Running");
    }
    public List<TopicLeaderboardResponse> getTopicLeaderboard() {

        Map<String, Integer> totalScores = new HashMap<>();
        Map<String, Integer> counts = new HashMap<>();

        for (InterviewRecord record : interviewRecordRepository.findAll()) {
            String category = record.getCategory();

            if (category == null || category.isBlank()) {
                category = "Unknown";
            }

            totalScores.put(category, totalScores.getOrDefault(category, 0) + record.getScore());
            counts.put(category, counts.getOrDefault(category, 0) + 1);
        }

        List<TopicLeaderboardResponse> leaderboard = new ArrayList<>();

        for (String category : totalScores.keySet()) {
            double average = (double) totalScores.get(category) / counts.get(category);
            leaderboard.add(new TopicLeaderboardResponse(category, average));
        }

        leaderboard.sort(Comparator.comparing(TopicLeaderboardResponse::getAverageScore).reversed());

        return leaderboard;
    }
}