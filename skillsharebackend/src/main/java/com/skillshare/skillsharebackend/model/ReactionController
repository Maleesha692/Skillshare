package com.skillshare.skillsharebackend.controller;

import com.skillshare.skillsharebackend.dto.ReactionRequest;
import com.skillshare.skillsharebackend.model.Notification;
import com.skillshare.skillsharebackend.model.Post;
import com.skillshare.skillsharebackend.model.PostReaction;
import com.skillshare.skillsharebackend.model.User;
import com.skillshare.skillsharebackend.repository.NotificationRepository;
import com.skillshare.skillsharebackend.repository.PostReactionRepository;
import com.skillshare.skillsharebackend.repository.PostRepository;
import com.skillshare.skillsharebackend.repository.UserRepository;
import com.skillshare.skillsharebackend.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reactions")
public class ReactionController {

    @Autowired
    private PostReactionRepository reactionRepo;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private NotificationService notificationService;

    @PostMapping
    public ResponseEntity<?> reactToPost(@RequestBody ReactionRequest request) {
        Optional<PostReaction> existing = reactionRepo.findByPostIdAndUserId(request.getPostId(), request.getUserId());
        Post post = postRepository.findById(request.getPostId()).orElseThrow();
        User sender = userRepository.findById(request.getUserId()).orElseThrow();
        if (existing.isPresent()) {
            PostReaction reaction = existing.get();
            if (reaction.getType().equals(request.getType())) {
                reactionRepo.delete(reaction); // remove reaction
                return ResponseEntity.ok("Reaction removed");
            } else {
                reaction.setType(request.getType()); // change reaction
                reactionRepo.save(reaction);
                return ResponseEntity.ok("Reaction updated");
            }
        } else {
            PostReaction newReaction = new PostReaction();
            newReaction.setPostId(request.getPostId());
            newReaction.setUserId(request.getUserId());
            newReaction.setType(request.getType());
            reactionRepo.save(newReaction);

            notificationService.createNotification(
                    post.getUser(),
                    sender,
                    post,
                    "reaction",
                    sender.getFullName() + " reacted to your post."
            );

            return ResponseEntity.ok("Reaction added");
        }
    }

    @GetMapping
    public ResponseEntity<?> getReactions(@RequestParam Long postId) {
        List<PostReaction> all = reactionRepo.findAllByPostId(postId);
        Map<String, Long> counts = all.stream()
                .collect(Collectors.groupingBy(PostReaction::getType, Collectors.counting()));

        return ResponseEntity.ok(counts);
    }


}

