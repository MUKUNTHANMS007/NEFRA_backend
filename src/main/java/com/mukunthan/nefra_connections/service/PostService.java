package com.mukunthan.nefra_connections.service;

import com.mukunthan.nefra_connections.dto.*;
import com.mukunthan.nefra_connections.entity.*;
import com.mukunthan.nefra_connections.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostLikeRepository postLikeRepository;
    private final PostCommentRepository postCommentRepository;

    @Transactional
    public PostResponseDTO createPost(PostCreateDTO request) {
        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Post post = new Post();
        post.setUser(user);
        post.setTitle(request.title());
        post.setDescription(request.description());
        post.setImageUrl(request.imageUrl());

        Post savedPost = postRepository.save(post);
        return mapToDTO(savedPost);
    }

    @Transactional(readOnly = true)
    public List<PostResponseDTO> getHomeFeed() {
        return postRepository.findAllByOrderByCreatedAtDesc().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void toggleLike(Long postId, Long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        postLikeRepository.findByPostIdAndUserId(postId, userId).ifPresentOrElse(
                postLikeRepository::delete, // If it exists, unlike it
                () -> { // If it doesn't exist, like it
                    PostLike like = new PostLike();
                    like.setPost(post);
                    like.setUser(user);
                    postLikeRepository.save(like);
                }
        );
    }

    @Transactional
    public void addComment(Long postId, CommentCreateDTO request) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        PostComment comment = new PostComment();
        comment.setPost(post);
        comment.setUser(user);
        comment.setContent(request.content());

        postCommentRepository.save(comment);
    }

    private PostResponseDTO mapToDTO(Post post) {
        return new PostResponseDTO(
                post.getId(),
                post.getUser().getId(),
                post.getUser().getFullName(),
                post.getUser().getProfileImageUrl(),
                post.getTitle(),
                post.getDescription(),
                post.getImageUrl(),
                post.getCreatedAt(),
                post.getLikeCount() == null ? 0 : post.getLikeCount(),
                post.getCommentCount() == null ? 0 : post.getCommentCount()
        );
    }
}