package com.skillshare.skillsharebackend.repository;

import com.skillshare.skillsharebackend.model.Follow;
import com.skillshare.skillsharebackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {
    Optional<Follow> findByFollowerAndFollowing(User follower, User following);
    List<Follow> findByFollower(User follower);
    List<Follow> findByFollowing(User following);
    void deleteByFollowerAndFollowing(User follower, User following);
}

