package com.mukunthan.nefra_connections.controller;

import com.mukunthan.nefra_connections.dto.SuccessStoryDto;
import com.mukunthan.nefra_connections.service.SuccessStoryService; // FIX: Added missing import
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/stories")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class StoryController {

    // FIX: Added 'private final' so @RequiredArgsConstructor can inject it
    private final SuccessStoryService successStoryService;

    @GetMapping("/featured")
    public List<SuccessStoryDto> getFeatured() {
        return successStoryService.getFeaturedStories();
    }
}