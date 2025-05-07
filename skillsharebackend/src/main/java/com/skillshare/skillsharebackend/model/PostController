package com.skillshare.skillsharebackend.controller;

import com.skillshare.skillsharebackend.dto.PostResponseDTO;
import com.skillshare.skillsharebackend.model.Post;
import com.skillshare.skillsharebackend.model.PostMedia;
import com.skillshare.skillsharebackend.model.ReactionType;
import com.skillshare.skillsharebackend.model.VideoUtil;
import com.skillshare.skillsharebackend.repository.PostRepository;
import com.skillshare.skillsharebackend.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;
    private final PostRepository postRepository;

    public PostController(PostService postService, PostRepository postRepository) {
        this.postService = postService;
        this.postRepository = postRepository;
    }

    @GetMapping("/user")
    public ResponseEntity<List<PostResponseDTO>> getPostsByUserId(@RequestParam("id") Long userId) {
        List<PostResponseDTO> posts = postService.getPostsByUserId(userId);
        return ResponseEntity.ok(posts);
    }


//    @PostMapping("/posts/{postId}/react")
//    public ResponseEntity<?> reactToPost(
//            @PathVariable Long postId,
//            @RequestParam ReactionType type,
//            @RequestParam Long userId
//    ) {
//        postService.reactToPost(postId, userId, type);
//        return ResponseEntity.ok("Reaction updated");
//    }


    @GetMapping("/all")
    public ResponseEntity<List<PostResponseDTO>> getAllPosts(@RequestParam(required = false) Long userId) {
        List<PostResponseDTO> posts = postService.getAllPostResponses();
        return ResponseEntity.ok(posts);
    }


//    @GetMapping("/all")
//    public ResponseEntity<List<PostResponseDTO>> getAllPosts() {
//        List<PostResponseDTO> posts = postService.getAllPostResponses();
//        return ResponseEntity.ok(posts);
//    }


    @PostMapping("/upload")
    public ResponseEntity<?> uploadPost(
            @RequestParam("caption") String caption,
            @RequestParam("mediaFiles") List<MultipartFile> mediaFiles,
            @RequestParam("userId") Long userId
    ) {
        try {
            if (mediaFiles.size() > 3) {
                return ResponseEntity.badRequest().body("You can upload a maximum of 3 files.");
            }

            Post post = new Post();
            post.setCaption(caption);
            post.setUserId(userId);

            List<PostMedia> mediaList = new ArrayList<>();
            for (MultipartFile file : mediaFiles) {
                String contentType = file.getContentType();
                String mediaType = contentType.startsWith("video") ? "video" : "image";

                if (mediaType.equals("video")) {
                    long maxDurationSeconds = 30;
                    if (!VideoUtil.isDurationValid(file.getInputStream(), maxDurationSeconds)) {
                        return ResponseEntity.badRequest().body("Video exceeds 30 seconds.");
                    }
                }

                String url = postService.saveMediaFile(file);
                PostMedia media = new PostMedia();
                media.setMediaUrl(url);
                media.setMediaType(mediaType);
                media.setPost(post); // Link to parent post
                mediaList.add(media);
            }

            post.setMediaList(mediaList);
            postService.savePost(post);
            return ResponseEntity.ok("Post uploaded successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Upload failed");
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updatePost(
            @RequestParam Long postId,
            @RequestParam String caption,
            @RequestParam(value = "media", required = false) MultipartFile media
    ) {
        try {
            Post updatedPost = postService.updatePost(postId, caption, media);
            PostResponseDTO dto = postService.convertToResponseDTO(updatedPost);
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Post update failed: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deletePost(@RequestParam Long postId) {
        try {
            postService.deletePost(postId);
            return ResponseEntity.ok("Post deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete post: " + e.getMessage());
        }
    }



}
