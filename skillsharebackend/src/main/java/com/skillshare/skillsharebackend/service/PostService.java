package com.skillshare.skillsharebackend.service;

import com.skillshare.skillsharebackend.dto.PostResponseDTO;
import com.skillshare.skillsharebackend.dto.UserResponseDTO;
import com.skillshare.skillsharebackend.model.*;
import com.skillshare.skillsharebackend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final Path root = Paths.get("uploads");
    private final String uploadDir = System.getProperty("user.dir") + File.separator + "uploads";


    @Autowired
    private PostRepository postRepository;

    // Inject these repositories into PostService
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private PostReactionRepository postReactionRepository;

    @Autowired
    private PostMediaRepository postMediaRepository;

    public PostResponseDTO convertToResponseDTO(Post post) {
        User user = post.getUser();
        UserResponseDTO userDTO = new UserResponseDTO(
                user.getId(),
                user.getFullName(),
                user.getPhoto()
        );

        PostResponseDTO dto = new PostResponseDTO();
        dto.setId(post.getId());
        dto.setCaption(post.getCaption());
        dto.setCreatedAt(post.getCreatedAt());
        dto.setUser(userDTO);
        dto.setMediaList(post.getMediaList());
        return dto;
    }


    public Post updatePost(Long postId, String caption, MultipartFile media) throws IOException {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        post.setCaption(caption);

        if (media != null && !media.isEmpty()) {
            String filename = UUID.randomUUID() + "_" + media.getOriginalFilename();
            Path mediaPath = Paths.get(uploadDir, filename);
            Files.copy(media.getInputStream(), mediaPath, StandardCopyOption.REPLACE_EXISTING);

            PostMedia postMedia = new PostMedia();
            postMedia.setMediaUrl("/uploads/" + filename);
            postMedia.setMediaType(media.getContentType().startsWith("video") ? "video" : "image");
            postMedia.setPost(post);

            post.getMediaList().clear(); // Optional: if you want to replace old media
            post.getMediaList().add(postMedia);
        }

        return postRepository.save(post);
    }


    public List<PostResponseDTO> getPostsByUserId(Long userId) {
        // Make sure your repository method supports sorting
        List<Post> posts = postRepository.findByUserId(userId, Sort.by(Sort.Direction.DESC, "createdAt"));

        return posts.stream().map(post -> {
            User user = post.getUser();
            UserResponseDTO userDTO = new UserResponseDTO(
                    user.getId(),
                    user.getFullName(),
                    user.getPhoto()
            );

            PostResponseDTO dto = new PostResponseDTO();
            dto.setId(post.getId());
            dto.setCaption(post.getCaption());
            dto.setCreatedAt(post.getCreatedAt());
            dto.setUser(userDTO);
            dto.setMediaList(post.getMediaList());
            return dto;
        }).collect(Collectors.toList());
    }



    public String saveMediaFile(MultipartFile file) throws IOException {
        if (!Files.exists(root)) Files.createDirectories(root);
        String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Files.copy(file.getInputStream(), root.resolve(filename), StandardCopyOption.REPLACE_EXISTING);
        return "/uploads/" + filename;
    }

    public void savePost(Post post) {
        postRepository.save(post);
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public List<PostResponseDTO> getAllPostResponses() {
//        List<Post> posts = postRepository.findAll();
        List<Post> posts = postRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));

        return posts.stream().map(post -> {
            User user = post.getUser(); // fetched via @ManyToOne
            UserResponseDTO userDTO = new UserResponseDTO(
                    user.getId(),
                    user.getFullName(),
                    user.getPhoto()
            );

            PostResponseDTO dto = new PostResponseDTO();
            dto.setId(post.getId());
            dto.setCaption(post.getCaption());
            dto.setCreatedAt(post.getCreatedAt());
            dto.setUser(userDTO);
            dto.setMediaList(post.getMediaList());
            return dto;
        }).collect(Collectors.toList());
    }


    public void deletePost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        // 1. Delete Comments linked to the Post
        commentRepository.deleteAll(commentRepository.findByPostOrderByCreatedAtAsc(post));

        // 2. Delete Reactions linked to the Post
        List<PostReaction> reactions = postReactionRepository.findAllByPostId(postId);
        postReactionRepository.deleteAll(reactions);

        // 3. Delete Notifications linked to the Post
        List<Notification> notifications = notificationRepository.findAll()
                .stream()
                .filter(n -> n.getPost() != null && n.getPost().getId().equals(postId))
                .toList();
        notificationRepository.deleteAll(notifications);

        // 4. Delete Media linked to the Post
        postMediaRepository.deleteAll(post.getMediaList());

        // 5. Finally Delete the Post
        postRepository.delete(post);
    }



}
