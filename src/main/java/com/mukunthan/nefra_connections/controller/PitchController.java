package com.mukunthan.nefra_connections.controller;

import com.mukunthan.nefra_connections.entity.Pitch;
import com.mukunthan.nefra_connections.repository.PitchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/pitches")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class PitchController {

    private final PitchRepository pitchRepository;

    @GetMapping
    public List<Pitch> getAllPitches() {
        return pitchRepository.findAll();
    }
}