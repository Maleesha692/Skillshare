package com.skillshare.skillsharebackend.dto;

import com.skillshare.skillsharebackend.model.User;

public class UserDTO {
    private Long id;
    private String fullName;
    private String photo;  // Optional field
    private String bio;
    private String location;
    private String email;

    // Constructors
    public UserDTO() {}
    public UserDTO(User user) {
        this.id = user.getId();
        this.fullName = user.getFullName(); // Make sure this matches your User entity
        this.photo = user.getPhoto();
        this.bio = user.getBio();
        this.location = user.getLocation();
        this.email = user.getEmail();
    }

    public UserDTO(Long id, String fullName, String photo, String bio) {
        this.id = id;
        this.fullName = fullName;
        this.photo = photo;
        this.bio = bio;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
