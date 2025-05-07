package com.skillshare.skillsharebackend.controller;

import com.skillshare.skillsharebackend.dto.CommentRequest;
import com.skillshare.skillsharebackend.dto.CommentResponse;
import com.skillshare.skillsharebackend.model.Comment;
import com.skillshare.skillsharebackend.model.Notification;
import com.skillshare.skillsharebackend.model.Post;
import com.skillshare.skillsharebackend.model.User;
import com.skillshare.skillsharebackend.repository.CommentRepository;
import com.skillshare.skillsharebackend.repository.NotificationRepository;
import com.skillshare.skillsharebackend.repository.PostRepository;
import com.skillshare.skillsharebackend.repository.UserRepository;

import com.skillshare.skillsharebackend.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentRepository commentRepo;

    @Autowired
    private PostRepository postRepo;

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private CommentRepository commentRepository;

    // ✅ UPDATE COMMENT
    // Edit Comment
    @PutMapping("/update")
    public ResponseEntity<?> editComment(
            @RequestParam Long commentId,
            @RequestBody CommentRequest request
    ) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        // Update comment content
        comment.setContent(request.getContent());
        commentRepository.save(comment);

        return ResponseEntity.ok("Comment updated successfully");
    }

    // ✅ DELETE COMMENT
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteComment(@RequestParam Long commentId) {
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        if (optionalComment.isEmpty()) {
            return ResponseEntity.badRequest().body("Comment not found");
        }

        commentRepository.deleteById(commentId);
        return ResponseEntity.ok("Comment deleted successfully");
    }


    @PostMapping
    public CommentResponse addComment(@RequestBody CommentRequest request) {
        Post post = postRepo.findById(request.getPostId()).orElseThrow();
        User user = userRepo.findById(request.getUserId()).orElseThrow();

        Comment comment = new Comment();
        comment.setContent(request.getContent());
        comment.setPost(post);
        comment.setUser(user);
        comment.setCreatedAt(java.time.LocalDateTime.now());

        Comment saved = commentRepo.save(comment);

        // ✅ Trigger Notification to Post Owner
//        if (!post.getUser().getId().equals(user.getId())) { // prevent self-notification
//            Notification notification = new Notification();
//            notification.setRecipient(post.getUser());
//            notification.setSender(user);
//            notification.setPost(post);
//            notification.setType("comment");
//            notification.setContent(user.getFullName() + " commented on your post.");
//            notificationRepository.save(notification);
//        }
        notificationService.createNotification(
                post.getUser(), // recipient
                user,           // sender
                post,
                "comment",
                user.getFullName() + " commented on your post."
        );


        CommentResponse response = new CommentResponse();
        response.setContent(saved.getContent());
        response.setUsername(user.getFullName());
        response.setCreatedAt(saved.getCreatedAt());

        return response;
    }

    @GetMapping
    public List<CommentResponse> getComments(@RequestParam Long postId) {
        Post post = postRepo.findById(postId).orElseThrow();
        List<Comment> comments = commentRepo.findByPostOrderByCreatedAtAsc(post);

        return comments.stream().map(comment -> {
            CommentResponse res = new CommentResponse();
            res.setId(comment.getId());  // Set the comment id here
            res.setContent(comment.getContent());
            res.setUsername(comment.getUser().getFullName());
            res.setCreatedAt(comment.getCreatedAt());
            return res;
        }).collect(Collectors.toList());
    }


    @GetMapping("/count")
    public Long getCommentCount(@RequestParam Long postId) {
        Post post = postRepo.findById(postId).orElseThrow();
        return commentRepo.countByPost(post);
    }
}
