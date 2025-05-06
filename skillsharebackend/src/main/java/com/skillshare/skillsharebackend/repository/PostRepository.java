package com.skillshare.skillsharebackend.repository;

import com.skillshare.skillsharebackend.model.Post;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    // Fetch all posts with their media using JOIN FETCH
    @EntityGraph(attributePaths = {"mediaList"})
    List<Post> findAll();

    // Optional: Fetch posts by user ID with media
    @EntityGraph(attributePaths = {"mediaList"})
    List<Post> findByUserId(Long userId, Sort sort);
}
