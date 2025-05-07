package com.skillshare.skillsharebackend.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.skillshare.skillsharebackend.model.Notification;
import com.skillshare.skillsharebackend.model.Post;
import com.skillshare.skillsharebackend.model.User;
import com.skillshare.skillsharebackend.repository.NotificationRepository;


@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepo;

    public void createNotification(User recipient, User sender, Post post, String type, String content) {
        if (recipient.getId().equals(sender.getId())) {
            // Don't notify self
            return;
        }

        Notification notification = new Notification();
        notification.setRecipient(recipient);
        notification.setSender(sender);
        notification.setPost(post);
        notification.setType(type);
        notification.setContent(content);

        notificationRepo.save(notification);
        System.out.println("✅ Notification saved: " + content);
    }
}
