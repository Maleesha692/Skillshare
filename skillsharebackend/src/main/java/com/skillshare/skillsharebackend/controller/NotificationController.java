package com.skillshare.skillsharebackend.controller;

import com.skillshare.skillsharebackend.dto.NotificationRequest;
import com.skillshare.skillsharebackend.dto.NotificationResponse;
import com.skillshare.skillsharebackend.model.Notification;
import com.skillshare.skillsharebackend.model.Post;
import com.skillshare.skillsharebackend.model.User;
import com.skillshare.skillsharebackend.repository.NotificationRepository;
import com.skillshare.skillsharebackend.repository.PostRepository;
import com.skillshare.skillsharebackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationRepository notificationRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PostRepository postRepo;

    @PostMapping
    public void addNotification(@RequestBody NotificationRequest request) {
        User recipient = userRepo.findById(request.getRecipientId()).orElseThrow();
        User sender = userRepo.findById(request.getSenderId()).orElseThrow();
        //Post post = postRepo.findById(request.getPostId()).orElseThrow();

        Notification notification = new Notification();
        notification.setRecipient(recipient);
        notification.setSender(sender);
        if (request.getPostId() != null) {
            Post post = postRepo.findById(request.getPostId()).orElseThrow();
            notification.setPost(post);
        } else {
            notification.setPost(null); // make sure to handle nulls in your DB
        }
        notification.setType(request.getType());
        notification.setContent(request.getContent());

        notificationRepo.save(notification);
    }

    @GetMapping
    public List<NotificationResponse> getUserNotifications(@RequestParam Long userId) {
        User user = userRepo.findById(userId).orElseThrow();
        return notificationRepo.findByRecipientOrderByTimestampDesc(user)
                .stream()
                .map(n -> {
                    NotificationResponse res = new NotificationResponse();
                    res.setContent(n.getContent());
                    res.setSenderName(n.getSender().getFullName());
                    res.setRead(n.isRead());
                    res.setTimestamp(n.getTimestamp());
                    return res;
                })
                .collect(Collectors.toList());
    }

    @GetMapping("/unread-count")
    public Long getUnreadCount(@RequestParam Long userId) {
        User user = userRepo.findById(userId).orElseThrow();
        return notificationRepo.countByRecipientAndIsReadFalse(user);
    }

    @PostMapping("/mark-read")
    public void markAllAsRead(@RequestParam Long userId) {
        User user = userRepo.findById(userId).orElseThrow();
        List<Notification> notifs = notificationRepo.findByRecipientOrderByTimestampDesc(user);
        for (Notification n : notifs) {
            n.setRead(true);
        }
        notificationRepo.saveAll(notifs);
    }
}
