package com.skillshare.skillsharebackend.service;

import com.skillshare.skillsharebackend.dto.FollowDTO;
import com.skillshare.skillsharebackend.dto.UserDTO;
import com.skillshare.skillsharebackend.model.Follow;
import com.skillshare.skillsharebackend.model.User;
import com.skillshare.skillsharebackend.repository.FollowRepository;
import com.skillshare.skillsharebackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FollowService {

    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private NotificationService notificationService;

    public boolean isFollowing(User follower, User following) {
        return followRepository.findByFollowerAndFollowing(follower, following).isPresent();
    }

    public void followUser(FollowDTO followDTO, User follower) {
        User following = userRepository.findById(followDTO.getFollowingId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!isFollowing(follower, following)) {
            Follow follow = new Follow();
            follow.setFollower(follower);
            follow.setFollowing(following);


            // After saving follow relationship
            notificationService.createNotification(
                    following, // recipient (the user being followed)
                    follower,  // sender (the user following)
                    null,      // postId is null
                    "follow",
                    follower.getFullName() + " started following you."
            );
            followRepository.save(follow);

        }
    }

    public void unfollowUser(FollowDTO followDTO, User follower) {
        User following = userRepository.findById(followDTO.getFollowingId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        followRepository.findByFollowerAndFollowing(follower, following)
                .ifPresent(follow -> followRepository.delete(follow));
        // ✅ Send "unfollow" notification
        notificationService.createNotification(
                following, // recipient
                follower,  // sender
                null,      // no post related
                "unfollow",
                follower.getFullName() + " unfollowed you."
        );
    }

    public List<UserDTO> getFollowers(User user) {
        List<Follow> follows = followRepository.findByFollowing(user);
        return follows.stream()
                .map(follow -> new UserDTO(follow.getFollower().getId(), follow.getFollower().getFullName(), follow.getFollower().getPhoto(), follow.getFollower().getBio()))
                .collect(Collectors.toList());
    }

    public List<UserDTO> getFollowing(User user) {
        List<Follow> follows = followRepository.findByFollower(user);
        return follows.stream()
                .map(follow -> new UserDTO(follow.getFollowing().getId(), follow.getFollowing().getFullName(), follow.getFollowing().getPhoto(),follow.getFollowing().getBio()))
                .collect(Collectors.toList());
    }
}
