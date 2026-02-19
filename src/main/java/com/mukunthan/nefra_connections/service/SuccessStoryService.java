package com.mukunthan.nefra_connections.service;

import com.mukunthan.nefra_connections.dto.SuccessStoryDto;
import com.mukunthan.nefra_connections.entity.SuccessStory;
import com.mukunthan.nefra_connections.repository.SuccessStoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SuccessStoryService {

    private final SuccessStoryRepository repository;

    public List<SuccessStoryDto> getFeaturedStories() {
        // Fetches all stories from the DB and maps them to DTOs
        return repository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private SuccessStoryDto convertToDto(SuccessStory story) {
        SuccessStoryDto dto = new SuccessStoryDto();
        dto.setId(story.getId());
        dto.setTitle(story.getTitle());
        dto.setMetric(story.getMetric());
        dto.setOutcome(story.getOutcome());
        dto.setAuthor(story.getAuthor());
        dto.setCompany(story.getCompany());
        dto.setImageUrl(story.getImageUrl());
        return dto;
    }
}