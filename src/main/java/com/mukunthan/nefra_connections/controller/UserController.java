package com.mukunthan.nefra_connections.controller;

import com.mukunthan.nefra_connections.dto.UserDto;
import com.mukunthan.nefra_connections.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

    private final UserService userService;

    @GetMapping("/featured")
    public List<UserDto> getFeatured() {
        // This calls the service we created to get Mukunthan, Siddiq, Jensen, and Elon
        return userService.getFeaturedUsers();
    }
}