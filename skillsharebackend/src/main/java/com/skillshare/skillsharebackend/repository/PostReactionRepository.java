package com.skillshare.skillsharebackend.repository;

import com.skillshare.skillsharebackend.model.Post;
import com.skillshare.skillsharebackend.model.PostReaction;
import com.skillshare.skillsharebackend.model.ReactionType;
import com.skillshare.skillsharebackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostReactionRepository extends JpaRepository<PostReaction, Long> {
    Optional<PostReaction> findByPostIdAndUserId(Long postId, Long userId);
    List<PostReaction> findAllByPostId(Long postId);
}