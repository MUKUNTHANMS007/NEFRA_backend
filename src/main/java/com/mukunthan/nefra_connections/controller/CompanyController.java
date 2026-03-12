package com.mukunthan.nefra_connections.controller;

import com.mukunthan.nefra_connections.entity.Company;
import com.mukunthan.nefra_connections.repository.CompanyRepository;
import com.mukunthan.nefra_connections.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/companies")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class CompanyController {

    private final CompanyService companyService;
    private final CompanyRepository companyRepository;

    // INJECT THE SECURE API KEY FROM application.properties
    @Value("${gemini.api.key}")
    private String geminiApiKey;

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCompany(@PathVariable Long id, @RequestBody Company data, @RequestParam Long requesterId) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Company not found"));

        if (!company.getUser().getId().equals(requesterId)) {
            return ResponseEntity.status(403).body("Access Denied: You do not own this entity.");
        }

        return ResponseEntity.ok(companyService.saveOrUpdateCompany(data, requesterId));
    }

    @GetMapping("/my-company")
    public ResponseEntity<Company> getMyCompany(@RequestParam Long userId) {
        return companyRepository.findByUser_Id(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    @PostMapping
    public ResponseEntity<Company> saveCompany(@RequestBody Company company, @RequestParam Long userId) {
        return ResponseEntity.ok(companyService.saveOrUpdateCompany(company, userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Company> getById(@PathVariable Long id) {
        return companyRepository.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // --- THE NEW AI ANALYSIS ENDPOINT ---
    // --- THE FIXED AI ANALYSIS ENDPOINT ---
    @GetMapping("/{id}/analyze")
    public ResponseEntity<?> analyzeCompany(@PathVariable Long id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Company not found"));

        try {
            // 1. Build the strategic prompt
            // 1. Build the strategic, highly-constrained VC prompt
            String prompt = String.format(
                    "Act as a Senior Partner at a Tier-1 Venture Capital firm. Write a strict 3-sentence executive investment memo for the following startup:\n" +
                            "Name: %s\nSector: %s\nTagline: %s\nLocation: %s\nDescription: %s\n\n" +
                            "Formatting Rules:\n" +
                            "- Sentence 1: Define the core value proposition and the specific market friction it solves.\n" +
                            "- Sentence 2: Analyze the technical scalability and operational mechanics.\n" +
                            "- Sentence 3: Provide a clinical, bullish investment outlook.\n" +
                            "STRICTLY FORBIDDEN: Do not use marketing fluff, flowery language, or buzzwords like 'burgeoning', 'revolutionize', 'conduit', or 'synergy'. Keep the tone ruthless, analytical, concise, and highly professional.",
                    company.getName(), company.getDomainType(), company.getTagline(), company.getLocation(), company.getDescription()
            );

            // 2. THE FIX: Changed model to 'gemini-pro' which is universally supported in v1beta
            String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=" + geminiApiKey;

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, Object> requestBody = Map.of(
                    "contents", List.of(
                            Map.of("parts", List.of(
                                    Map.of("text", prompt)
                            ))
                    )
            );

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);
            RestTemplate restTemplate = new RestTemplate();

            // 3. Execute request and extract the text
            ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);
            Map<String, Object> body = response.getBody();
            List<Map<String, Object>> candidates = (List<Map<String, Object>>) body.get("candidates");
            Map<String, Object> content = (Map<String, Object>) candidates.get(0).get("content");
            List<Map<String, Object>> parts = (List<Map<String, Object>>) content.get("parts");
            String text = (String) parts.get(0).get("text");

            // Return proper JSON map
            return ResponseEntity.ok(Map.of("analysis", text));

        } catch (Exception e) {
            // Log the actual Google error so you can see it in your terminal
            System.err.println("AI_CORE_FAILURE: " + e.getMessage());

            // Return a safe 500 error in JSON format so the frontend doesn't crash or throw fake CORS errors
            return ResponseEntity.status(500).body(Map.of("error", "Synthesis failure. Verification of backend uplink required."));
        }
    }
}