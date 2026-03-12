package com.mukunthan.nefra_connections.controller;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/ai")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true", allowedHeaders = "*")
public class AIController {

    // 1. Tell Spring to grab the key from application.properties
    @Value("${gemini.api.key}")
    private String geminiApiKey;

    // 2. Declare the client, BUT DO NOT INITIALIZE IT YET
    private Client client;

    // 3. Let Spring build the client AFTER it has the API key
    @PostConstruct
    public void init() {
        this.client = Client.builder().apiKey(geminiApiKey).build();
        System.out.println("NEFRA AI Core: Initialized successfully.");
    }
    // Simplified dedicated endpoint in Spring Boot
    @PostMapping("/api/v1/ai/analyze-company")
    public ResponseEntity<String> analyzeCompany(@RequestBody Map<String, String> request) {
        String companyName = request.get("name");
        String bio = request.get("bio");

        // Force Gemini to give us actionable insights, not a conversation
        String prompt = String.format(
                "Act as a professional startup analyst. Based on this bio: '%s', provide an executive analysis for a company named '%s'. " +
                        "Focus on: 1. Market Opportunity, 2. Growth Trajectory, 3. Critical Risks. " +
                        "Use Markdown formatting for headings.", bio, companyName
        );

        try {
            GenerateContentResponse response = client.models.generateContent("gemini-1.5-flash", prompt, null);
            return ResponseEntity.ok(response.text());
        } catch (Exception e) {
            // Log errors properly, never return 200 with an error msg
            return ResponseEntity.status(500).body("Analysis offline.");
        }
    }

    @PostMapping("/ask")
    public ResponseEntity<String> askGemini(@RequestBody Map<String, String> request) {
        String prompt = request.get("prompt");

        if (prompt == null || prompt.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Prompt cannot be empty");
        }

        try {
            GenerateContentResponse response = client.models.generateContent(
                    "gemini-2.5-flash-lite",
                    prompt,
                    null
            );

            String actualModelUsed = response.modelVersion().orElse("Unknown Model");
            System.out.println("DEBUG - Model actually used by Google: " + actualModelUsed);

            return ResponseEntity.ok(response.text());

        } catch (Exception e) {
            System.err.println("AI Error: " + e.getMessage());
            return ResponseEntity.status(500).body("Error communicating with AI core.");
        }
    }
}