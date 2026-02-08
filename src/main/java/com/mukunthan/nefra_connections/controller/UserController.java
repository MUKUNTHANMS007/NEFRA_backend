package com.mukunthan.nefra_connections.controller;

import com.mukunthan.nefra_connections.model.User;
import org.springframework.web.bind.annotation.*;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

    @GetMapping
    public List<User> getUsers() {
        return Arrays.asList(
                // Update these two lines in your getUsers() method in UserController.java
                new User(1L, "Mukunthan", "Entrepreneur", "Building NEFRA Connections for PSG iTech.", "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=400"),
                new User(2L, "Axtr Investor", "Investor", "Looking for high-potential startups.", "https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?w=400")
        );
    }
}