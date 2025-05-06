package com.skillshare.skillsharebackend.dto;

public class UserResponseDTO {
    private Long id;
    private String fullName;
    private String photo;

    public UserResponseDTO(Long id, String fullName, String photo) {
        this.id = id;
        this.fullName = fullName;
        this.photo = photo;
    }

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
