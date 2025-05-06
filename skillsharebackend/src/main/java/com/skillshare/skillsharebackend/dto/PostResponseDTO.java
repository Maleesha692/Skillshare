package com.skillshare.skillsharebackend.dto;

import com.skillshare.skillsharebackend.model.PostMedia;
import com.skillshare.skillsharebackend.model.ReactionType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class PostResponseDTO {
    private Long id;
    private String caption;
    private LocalDateTime createdAt;
    private UserResponseDTO user;
    private List<PostMedia> mediaList;
    private Map<ReactionType, Long> reactions;
    private ReactionType userReaction;



    // Constructors, getters, setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public UserResponseDTO getUser() {
        return user;
    }

    public void setUser(UserResponseDTO user) {
        this.user = user;
    }

    public List<PostMedia> getMediaList() {
        return mediaList;
    }

    public void setMediaList(List<PostMedia> mediaList) {
        this.mediaList = mediaList;
    }

    public Map<ReactionType, Long> getReactions() {
        return reactions;
    }

    public void setReactions(Map<ReactionType, Long> reactions) {
        this.reactions = reactions;
    }

    public ReactionType getUserReaction() {
        return userReaction;
    }

    public void setUserReaction(ReactionType userReaction) {
        this.userReaction = userReaction;
    }
}