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
import scorp.socialmedia.like.dto.CreateLikeRequest;
import scorp.socialmedia.like.dto.LikeResponse;
import scorp.socialmedia.like.service.ILikeService;

/**
 * REST controller for like management operations.
 * Provides endpoints for creating and retrieving likes.
 */
@RestController
@RequestMapping("/likes")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Like Management", description = "APIs for managing likes")
public class LikeController {

    private final ILikeService likeService;

    /**
     * Creates a new like relationship.
     *
     * @param request the like creation request
     * @return the created like relationship
     */
    @PostMapping
    @Operation(summary = "Like a post", description = "Creates a like relationship between a user and a post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Post liked successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "User or post not found"),
            @ApiResponse(responseCode = "409", description = "Post already liked by user")
    })
    public ResponseEntity<LikeResponse> likePost(
            @Valid @RequestBody CreateLikeRequest request) {
        
        log.info("User {} liking post {}", request.userId(), request.postId());
        
        LikeResponse response = likeService.likePost(request);
        
        log.info("Post liked successfully with ID: {}", response.id());
        
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Retrieves all likes with pagination.
     *
     * @param page the page number (0-based)
     * @param size the page size
     * @return paginated list of likes
     */
    @GetMapping
    @Operation(summary = "Get all likes", description = "Retrieves a paginated list of all likes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Likes retrieved successfully")
    })
    public ResponseEntity<Page<LikeResponse>> getAllLikes(
            @Parameter(description = "Page number (0-based)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size", example = "20")
            @RequestParam(defaultValue = "20") int size) {
        
        log.info("Retrieving likes - page: {}, size: {}", page, size);
        
        Page<LikeResponse> likes = likeService.getAllLikes(page, size);
        
        log.info("Retrieved {} likes", likes.getTotalElements());
        
        return ResponseEntity.ok(likes);
    }

    /**
     * Retrieves likes for a specific post.
     *
     * @param postId the post ID
     * @param page the page number (0-based)
     * @param size the page size
     * @return paginated list of likes for the post
     */
    @GetMapping("/post/{postId}")
    @Operation(summary = "Get likes for a post", description = "Retrieves all likes for a specific post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post likes retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Post not found")
    })
    public ResponseEntity<Page<LikeResponse>> getLikesByPost(
            @Parameter(description = "Post ID", required = true, example = "1")
            @PathVariable Long postId,
            @Parameter(description = "Page number (0-based)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size", example = "20")
            @RequestParam(defaultValue = "20") int size) {
        
        log.info("Retrieving likes for post ID: {} - page: {}, size: {}", postId, page, size);
        
        Page<LikeResponse> likes = likeService.getLikesByPost(postId, page, size);
        
        log.info("Retrieved {} likes for post ID: {}", likes.getTotalElements(), postId);
        
        return ResponseEntity.ok(likes);
    }

    /**
     * Retrieves likes by a specific user.
     *
     * @param userId the user ID
     * @param page the page number (0-based)
     * @param size the page size
     * @return paginated list of likes by the user
     */
    @GetMapping("/user/{userId}")
    @Operation(summary = "Get likes by user", description = "Retrieves all posts liked by a specific user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User likes retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<Page<LikeResponse>> getLikesByUser(
            @Parameter(description = "User ID", required = true, example = "1")
            @PathVariable Long userId,
            @Parameter(description = "Page number (0-based)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size", example = "20")
            @RequestParam(defaultValue = "20") int size) {
        
        log.info("Retrieving likes by user ID: {} - page: {}, size: {}", userId, page, size);
        
        Page<LikeResponse> likes = likeService.getLikesByUser(userId, page, size);
        
        log.info("Retrieved {} likes by user ID: {}", likes.getTotalElements(), userId);
        
        return ResponseEntity.ok(likes);
    }

    /**
     * Removes a like (unlikes a post).
     *
     * @param userId the user ID
     * @param postId the post ID
     * @return success response
     */
    @DeleteMapping("/{userId}/{postId}")
    @Operation(summary = "Unlike a post", description = "Removes a like relationship between a user and a post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Post unliked successfully"),
            @ApiResponse(responseCode = "404", description = "Like relationship not found")
    })
    public ResponseEntity<Void> unlikePost(
            @Parameter(description = "User ID", required = true, example = "1")
            @PathVariable Long userId,
            @Parameter(description = "Post ID", required = true, example = "1")
            @PathVariable Long postId) {
        
        log.info("User {} unliking post {}", userId, postId);
        
        likeService.unlikePost(userId, postId);
        
        log.info("Post unliked successfully: user {} unliked post {}", userId, postId);
        
        return ResponseEntity.noContent().build();
    }

    /**
     * Checks if a user has liked a specific post.
     *
     * @param userId the user ID
     * @param postId the post ID
     * @return boolean indicating if the post is liked
     */
    @GetMapping("/check/{userId}/{postId}")
    @Operation(summary = "Check if user liked post", description = "Checks if a specific user has liked a specific post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Like status retrieved successfully")
    })
    public ResponseEntity<Boolean> hasUserLikedPost(
            @Parameter(description = "User ID", required = true, example = "1")
            @PathVariable Long userId,
            @Parameter(description = "Post ID", required = true, example = "1")
            @PathVariable Long postId) {
        
        log.info("Checking if user {} liked post {}", userId, postId);
        
        boolean hasLiked = likeService.hasUserLikedPost(userId, postId);
        
        log.info("User {} {} post {}", userId, hasLiked ? "liked" : "has not liked", postId);
        
        return ResponseEntity.ok(hasLiked);
    }
}
