// UserSearchDTO.java
package com.skillshare.skillsharebackend.dto;

public class UserSearchDTO {
    private Long id;
    private String fullName;
    private String photo;  // Optional field

    public UserSearchDTO(Long id, String fullName, String photo) {
        this.id = id;
        this.fullName = fullName;
        this.photo = photo;
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
}
