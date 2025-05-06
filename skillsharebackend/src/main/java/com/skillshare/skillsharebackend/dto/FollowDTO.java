package com.skillshare.skillsharebackend.dto;

public class FollowDTO {
    private Long followerId;
    private Long followingId;

    // Constructors
    public FollowDTO() {}

    public FollowDTO(Long followerId, Long followingId) {
        this.followerId = followerId;
        this.followingId = followingId;
    }

    // Getters and Setters
    public Long getFollowerId() {
        return followerId;
    }

    public void setFollowerId(Long followerId) {
        this.followerId = followerId;
    }

    public Long getFollowingId() {
        return followingId;
    }

    public void setFollowingId(Long followingId) {
        this.followingId = followingId;
    }
}
