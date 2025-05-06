package com.skillshare.skillsharebackend.service;

import com.skillshare.skillsharebackend.dto.UserSearchDTO;
import com.skillshare.skillsharebackend.model.User;
import com.skillshare.skillsharebackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;


    // Set upload directory as an absolute path
    private final String uploadDir = System.getProperty("user.dir") + File.separator + "uploads";

    public User updateUserProfile(Long id, String fullName, String bio, MultipartFile photo) throws Exception {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new Exception("User not found"));

        user.setFullName(fullName);
        user.setBio(bio);

        if (photo != null && !photo.isEmpty()) {
            String fileName = System.currentTimeMillis() + "_" + photo.getOriginalFilename();

            File uploadPath = new File(uploadDir);
            if (!uploadPath.exists()) {
                uploadPath.mkdirs();
            }

            Path filePath = Paths.get(uploadDir, fileName);
            photo.transferTo(filePath.toFile());

            user.setPhoto(fileName);
        }

        return userRepository.save(user);
    }


    // UserService.java
    public List<UserSearchDTO> searchUsers(String query) {
        List<User> users = userRepository.findByFullNameContainingIgnoreCase((query));
        return users.stream()
                .map(u -> new UserSearchDTO(u.getId(), u.getFullName(), u.getPhoto()))
                .collect(Collectors.toList());
    }


    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }


    public User registerUser(User user, MultipartFile photo) throws Exception {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new Exception("Email already exists");
        }

        if (photo != null && !photo.isEmpty()) {
            String fileName = System.currentTimeMillis() + "_" + photo.getOriginalFilename();

            File uploadPath = new File(uploadDir);
            if (!uploadPath.exists()) {
                uploadPath.mkdirs(); // ✅ create folder if missing
            }

            Path filePath = Paths.get(uploadDir, fileName);
            photo.transferTo(filePath.toFile());

            // Save the photo path or filename in the user object
            user.setPhoto(fileName);
        }

        return userRepository.save(user);
    }
}
