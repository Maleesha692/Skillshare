package com.skillshare.skillsharebackend.repository;

import com.skillshare.skillsharebackend.model.Comment;
import com.skillshare.skillsharebackend.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostOrderByCreatedAtAsc(Post post);
    Long countByPost(Post post);
    Optional<Comment> findById(Long commentId);

}

