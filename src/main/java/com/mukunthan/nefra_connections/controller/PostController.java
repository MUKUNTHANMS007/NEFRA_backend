package com.mukunthan.nefra_connections.controller;

import com.mukunthan.nefra_connections.dto.*;
import com.mukunthan.nefra_connections.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostResponseDTO> createPost(@RequestBody PostCreateDTO request) {
        return ResponseEntity.ok(postService.createPost(request));
    }

    @GetMapping("/feed")
    public ResponseEntity<List<PostResponseDTO>> getHomeFeed() {
        return ResponseEntity.ok(postService.getHomeFeed());
    }

    @PostMapping("/{postId}/like")
    public ResponseEntity<String> toggleLike(
            @PathVariable Long postId,
            @RequestParam Long userId) {
        postService.toggleLike(postId, userId);
        return ResponseEntity.ok("Like toggled successfully");
    }

    @PostMapping("/{postId}/comments")
    public ResponseEntity<String> addComment(
            @PathVariable Long postId,
            @RequestBody CommentCreateDTO request) {
        postService.addComment(postId, request);
        return ResponseEntity.ok("Comment added successfully");
    }
}