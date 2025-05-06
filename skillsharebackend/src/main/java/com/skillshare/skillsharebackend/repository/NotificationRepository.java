package com.skillshare.skillsharebackend.repository;

import com.skillshare.skillsharebackend.model.Notification;
import com.skillshare.skillsharebackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByRecipientOrderByTimestampDesc(User recipient);
    long countByRecipientAndIsReadFalse(User recipient);
}
