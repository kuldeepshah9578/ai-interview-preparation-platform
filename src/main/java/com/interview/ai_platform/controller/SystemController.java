package com.interview.ai_platform.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.interview.ai_platform.model.InterviewRecord;
import com.interview.ai_platform.repository.InterviewRecordRepository;

@RestController
@RequestMapping("/api/v1/system")
public class SystemController {

    private final InterviewRecordRepository repository;

    public SystemController(InterviewRecordRepository repository) {
        this.repository = repository;
    }

    // 1. Health Check
    @GetMapping("/health")
    public Map<String, String> health() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        return response;
    }

    // 2. Count Records
    @GetMapping("/count")
    public Map<String, Long> count() {
        Map<String, Long> response = new HashMap<>();
        response.put("totalRecords", repository.count());
        return response;
    }

    // 3. Clear Database
    @DeleteMapping("/clear")
    public Map<String, String> clear() {
        repository.deleteAll();

        Map<String, String> response = new HashMap<>();
        response.put("message", "All records deleted");
        return response;
    }

    // 4. Score Range Filter
    @GetMapping("/score-range/{min}/{max}")
    public List<InterviewRecord> getByScoreRange(@PathVariable int min, @PathVariable int max) {
        return repository.findByScoreBetween(min, max);
    }

    // 5. Category Count
    @GetMapping("/category-count/{category}")
    public Map<String, Object> getCategoryCount(@PathVariable String category) {
        Map<String, Object> response = new HashMap<>();
        response.put("category", category);
        response.put("count", repository.countByCategoryIgnoreCase(category));
        return response;
    }

    // 6. Recent Count
    @GetMapping("/recent-count")
    public Map<String, Long> getRecentCount() {
        Map<String, Long> response = new HashMap<>();
        response.put("recentCount", repository.count());
        return response;
    }

    // 7. High Score Count
    @GetMapping("/high-score-count")
    public Map<String, Long> getHighScoreCount() {
        Map<String, Long> response = new HashMap<>();
        response.put("highScoreCount", repository.countByScoreGreaterThanEqual(8));
        return response;
    }

    // 8. Low Score Count
    @GetMapping("/low-score-count")
    public Map<String, Long> getLowScoreCount() {
        Map<String, Long> response = new HashMap<>();
        response.put("lowScoreCount", repository.countByScoreLessThanEqual(7));
        return response;
    }

    // 9. Category List
    @GetMapping("/category-list")
    public Set<String> getCategoryList() {
        List<InterviewRecord> records = repository.findAll();
        Set<String> categories = new HashSet<>();

        for (InterviewRecord record : records) {
            if (record.getCategory() != null && !record.getCategory().isBlank()) {
                categories.add(record.getCategory());
            }
        }

        return categories;
    }

    // 10. Latest Record
    @GetMapping("/latest-record")
    public InterviewRecord getLatestRecord() {
        return repository.findFirstByOrderByCreatedAtDesc().orElse(null);
    }

    // 11. Oldest Record
    @GetMapping("/oldest-record")
    public InterviewRecord getOldestRecord() {
        return repository.findFirstByOrderByCreatedAtAsc().orElse(null);
    }

    // 12. Score Summary
    @GetMapping("/score-summary")
    public Map<String, Long> getScoreSummary() {
        Map<String, Long> response = new HashMap<>();
        response.put("totalRecords", repository.count());
        response.put("highScoreCount", repository.countByScoreGreaterThanEqual(8));
        response.put("lowScoreCount", repository.countByScoreLessThanEqual(7));
        return response;
    }

    // 13. Java Count
    @GetMapping("/java-count")
    public Map<String, Long> getJavaCount() {
        Map<String, Long> response = new HashMap<>();
        response.put("javaCount", repository.countByCategoryIgnoreCase("Java"));
        return response;
    }

    // 14. Spring Count
    @GetMapping("/spring-count")
    public Map<String, Long> getSpringCount() {
        Map<String, Long> response = new HashMap<>();
        response.put("springCount", repository.countByCategoryIgnoreCase("Spring Boot"));
        return response;
    }

    // 15. Average Score by Category
    @GetMapping("/score-average-by-category/{category}")
    public Map<String, Object> getScoreAverageByCategory(@PathVariable String category) {
        List<InterviewRecord> records = repository.findByCategoryIgnoreCase(category);

        int total = 0;
        for (InterviewRecord record : records) {
            total += record.getScore();
        }

        double average = records.isEmpty() ? 0.0 : (double) total / records.size();

        Map<String, Object> response = new HashMap<>();
        response.put("category", category);
        response.put("averageScore", average);
        return response;
    }

    // 16. Exists by Id
    @GetMapping("/exists/{id}")
    public Map<String, Object> exists(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        response.put("id", id);
        response.put("exists", repository.existsById(id));
        return response;
    }

    // 17. Record Count by Difficulty
    @GetMapping("/record-count-by-difficulty/{difficulty}")
    public Map<String, Object> getRecordCountByDifficulty(@PathVariable String difficulty) {
        Map<String, Object> response = new HashMap<>();
        response.put("difficulty", difficulty);
        response.put("count", repository.countByDifficultyIgnoreCase(difficulty));
        return response;
    }

    // 18. Empty Check
    @GetMapping("/empty")
    public Map<String, Boolean> isEmpty() {
        Map<String, Boolean> response = new HashMap<>();
        response.put("empty", repository.count() == 0);
        return response;
    }

    // 19. Project Info
    @GetMapping("/project-info")
    public Map<String, String> getProjectInfo() {
        Map<String, String> response = new HashMap<>();
        response.put("project", "AI Interview Preparation Platform");
        response.put("version", "v1");
        response.put("backend", "Spring Boot");
        response.put("database", "H2");
        response.put("status", "Running");
        return response;
    }
}