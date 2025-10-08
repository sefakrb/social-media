package scorp.socialmedia.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import scorp.socialmedia.post.model.dto.*;
import scorp.socialmedia.post.service.IPostService;

import java.util.List;

/**
 * REST controller for post management operations.
 * Provides endpoints for creating, retrieving, and managing posts.
 */
@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Post Management", description = "APIs for managing posts")
public class PostController {

    private final IPostService postService;

    /**
     * Creates a new post.
     *
     * @param request the post creation request
     * @return the created post information
     */
    @PostMapping
    @Operation(summary = "Create a new post", description = "Creates a new post with the provided content")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Post created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<CreatePostResponse> createPost(
            @Valid @RequestBody CreatePostRequest request) {
        
        log.info("Creating new post for user ID: {}", request.userId());
        
        CreatePostResponse response = postService.createPost(request);
        
        log.info("Post created successfully with ID: {}", response.id());
        
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Retrieves all posts with pagination.
     *
     * @param page the page number (0-based)
     * @param size the page size
     * @return paginated list of posts
     */
    @GetMapping
    @Operation(summary = "Get all posts", description = "Retrieves a paginated list of all posts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Posts retrieved successfully")
    })
    public ResponseEntity<Page<PostResponse>> getAllPosts(
            @Parameter(description = "Page number (0-based)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size", example = "20")
            @RequestParam(defaultValue = "20") int size) {
        
        log.info("Retrieving posts - page: {}, size: {}", page, size);
        
        Page<PostResponse> posts = postService.getAllPosts(page, size);
        
        log.info("Retrieved {} posts", posts.getTotalElements());
        
        return ResponseEntity.ok(posts);
    }

    /**
     * Retrieves posts by user ID.
     *
     * @param userId the user ID
     * @param page the page number (0-based)
     * @param size the page size
     * @return paginated list of user's posts
     */
    @GetMapping("/user/{userId}")
    @Operation(summary = "Get posts by user", description = "Retrieves posts created by a specific user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Posts retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<Page<PostResponse>> getPostsByUser(
            @Parameter(description = "User ID", required = true, example = "1")
            @PathVariable Long userId,
            @Parameter(description = "Page number (0-based)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size", example = "20")
            @RequestParam(defaultValue = "20") int size) {
        
        log.info("Retrieving posts for user ID: {} - page: {}, size: {}", userId, page, size);
        
        Page<PostResponse> posts = postService.getPostsByUser(userId, page, size);
        
        log.info("Retrieved {} posts for user ID: {}", posts.getTotalElements(), userId);
        
        return ResponseEntity.ok(posts);
    }

    /**
     * Retrieves posts with user context (likes, follows).
     *
     * @param request the request containing post IDs and user context
     * @return list of posts with user context
     */
    @PostMapping("/context")
    @Operation(summary = "Get posts with context", description = "Retrieves posts with user-specific context like likes and follows")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Posts with context retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<List<PostWithContextResponse>> getPostsWithContext(
            @Valid @RequestBody GetPostsWithContextRequest request) {
        
        log.info("Retrieving posts with context for user ID: {}, post count: {}", 
                request.userId(), request.postIds().size());
        
        List<PostWithContextResponse> posts = postService.getPostsWithContext(request);
        
        log.info("Retrieved {} posts with context", posts.size());
        
        return ResponseEntity.ok(posts);
    }

    /**
     * Mixes posts by owners using the post mixing algorithm.
     *
     * @param request the list of posts to mix
     * @return mixed list of posts
     */
    @PostMapping("/mix")
    @Operation(summary = "Mix posts by owners", description = "Mixes posts from different owners using the mixing algorithm")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Posts mixed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<List<MixedPostResponse>> mixPostsByOwners(
            @Valid @RequestBody List<MixPostRequest> request) {
        
        log.info("Mixing {} posts by owners", request.size());
        
        List<MixedPostResponse> mixedPosts = postService.mixPostsByOwners(request);
        
        log.info("Successfully mixed {} posts", mixedPosts.size());
        
        return ResponseEntity.ok(mixedPosts);
    }
}
