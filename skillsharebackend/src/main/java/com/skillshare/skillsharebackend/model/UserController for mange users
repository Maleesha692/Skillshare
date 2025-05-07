package com.skillshare.skillsharebackend.controller;

import com.skillshare.skillsharebackend.dto.LoginRequest;
import com.skillshare.skillsharebackend.dto.UserDTO;
import com.skillshare.skillsharebackend.dto.UserSearchDTO;
import com.skillshare.skillsharebackend.model.User;
import com.skillshare.skillsharebackend.repository.UserRepository;
import com.skillshare.skillsharebackend.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
//@RequestMapping("/api/user")
//@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @PutMapping("/api/user/update")
    public ResponseEntity<?> updateUser(
            @RequestParam Long id,
            @RequestParam String fullName,
            @RequestParam String bio,
            @RequestParam(value = "photo", required = false) MultipartFile photo
    ) {
        try {
            User updatedUser = userService.updateUserProfile(id, fullName, bio, photo);
            return ResponseEntity.ok(updatedUser);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }



    @GetMapping("/api/user/profile")
    public ResponseEntity<?> getUserById(@RequestParam("id") Long id) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            // Assuming you have a DTO for clean response
            UserDTO userDTO = new UserDTO(user);
            return ResponseEntity.ok(userDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User not found with ID: " + id);
        }
    }



    @GetMapping("/api/user/search")
    public List<UserSearchDTO> searchUsers(@RequestParam("query") String fullName) {
        return userService.searchUsers(fullName);
    }

    @PostMapping("/api/user/register")
    public ResponseEntity<?> registerUser(
            @RequestParam String fullName,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String confirmPassword,
            @RequestParam String mobile,
            @RequestParam String bio,
            @RequestParam String location,
            @RequestParam(value = "photo", required = false) MultipartFile photo
    ) {
        try {
            if (!password.equals(confirmPassword)) {
                return ResponseEntity.badRequest().body("Passwords do not match");
            }

            User user = User.builder()
                    .fullName(fullName)
                    .email(email)
                    .password(password) // You should hash this in production
                    .mobile(mobile)
                    .bio(bio)
                    .location(location)
                    .build();

            User savedUser = userService.registerUser(user, photo);
            return ResponseEntity.ok(savedUser);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/api/user/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest request, HttpSession session) {
        Optional<User> optionalUser = userRepository.findByEmail(request.getEmail());

        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email");
        }

        User user = optionalUser.get();

        if (!user.getPassword().equals(request.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid password");
        }

        // ✅ Save user in session
        session.setAttribute("userData", user);
        return ResponseEntity.ok(user); // or send token/session, up to you
    }

//    @GetMapping("/api/user/session")
//    public ResponseEntity<?> getSessionUser(HttpSession session) {
//        User user = (User) session.getAttribute("userData");
//        if (user == null) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No user in session");
//        }
//        return ResponseEntity.ok(user);
//    }


    @GetMapping("/api/user/me")
    public ResponseEntity<?> getCurrentUser(@AuthenticationPrincipal OAuth2User principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
        }

        Map<String, Object> userDetails = Map.of(
                "name", principal.getAttribute("name"),
                "email", principal.getAttribute("email"),
                "picture", principal.getAttribute("picture")
        );

        return ResponseEntity.ok(userDetails);
    }
//    @GetMapping("/api/user/me")
//    public Map<String,Object> user(@AuthenticationPrincipal OAuth2User principal){
//        return principal.getAttributes();
//    }

}
