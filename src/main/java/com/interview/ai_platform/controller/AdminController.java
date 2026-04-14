package com.interview.ai_platform.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    // 1. Ping API
    @GetMapping("/ping")
    public Map<String, String> ping() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "API is alive");
        return response;
    }

    // 2. Version API
    @GetMapping("/version")
    public Map<String, String> version() {
        Map<String, String> response = new HashMap<>();
        response.put("version", "v1.0");
        return response;
    }

    // 3. Server Time API
    @GetMapping("/time")
    public Map<String, LocalDateTime> time() {
        Map<String, LocalDateTime> response = new HashMap<>();
        response.put("serverTime", LocalDateTime.now());
        return response;
    }
}