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
import scorp.socialmedia.follow.model.dto.CreateFollowRequest;
import scorp.socialmedia.follow.model.dto.FollowResponse;
import scorp.socialmedia.follow.service.IFollowService;

/**
 * REST controller for follow relationship management.
 * Provides endpoints for creating and retrieving follow relationships.
 */
@RestController
@RequestMapping("/follows")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Follow Management", description = "APIs for managing follow relationships")
public class FollowController {

    private final IFollowService followService;

    /**
     * Creates a new follow relationship.
     *
     * @param request the follow creation request
     * @return the created follow relationship
     */
    @PostMapping
    @Operation(summary = "Create a follow relationship", description = "Creates a follow relationship between two users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Follow relationship created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data or users cannot follow themselves"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "409", description = "Follow relationship already exists")
    })
    public ResponseEntity<FollowResponse> createFollow(
            @Valid @RequestBody CreateFollowRequest request) {
        
        log.info("Creating follow relationship: {} follows {}", request.followerId(), request.followingId());
        
        FollowResponse response = followService.createFollow(request);
        
        log.info("Follow relationship created successfully with ID: {}", response.id());
        
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Retrieves all follow relationships with pagination.
     *
     * @param page the page number (0-based)
     * @param size the page size
     * @return paginated list of follow relationships
     */
    @GetMapping
    @Operation(summary = "Get all follow relationships", description = "Retrieves a paginated list of all follow relationships")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Follow relationships retrieved successfully")
    })
    public ResponseEntity<Page<FollowResponse>> getAllFollows(
            @Parameter(description = "Page number (0-based)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size", example = "20")
            @RequestParam(defaultValue = "20") int size) {
        
        log.info("Retrieving follow relationships - page: {}, size: {}", page, size);
        
        Page<FollowResponse> follows = followService.getAllFollows(page, size);
        
        log.info("Retrieved {} follow relationships", follows.getTotalElements());
        
        return ResponseEntity.ok(follows);
    }

    /**
     * Retrieves users that a specific user is following.
     *
     * @param userId the user ID
     * @param page the page number (0-based)
     * @param size the page size
     * @return paginated list of users being followed
     */
    @GetMapping("/following/{userId}")
    @Operation(summary = "Get users being followed", description = "Retrieves users that a specific user is following")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Following list retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<Page<FollowResponse>> getFollowing(
            @Parameter(description = "User ID", required = true, example = "1")
            @PathVariable Long userId,
            @Parameter(description = "Page number (0-based)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size", example = "20")
            @RequestParam(defaultValue = "20") int size) {
        
        log.info("Retrieving following list for user ID: {} - page: {}, size: {}", userId, page, size);
        
        Page<FollowResponse> following = followService.getFollowing(userId, page, size);
        
        log.info("Retrieved {} following relationships for user ID: {}", following.getTotalElements(), userId);
        
        return ResponseEntity.ok(following);
    }

    /**
     * Retrieves users that follow a specific user.
     *
     * @param userId the user ID
     * @param page the page number (0-based)
     * @param size the page size
     * @return paginated list of followers
     */
    @GetMapping("/followers/{userId}")
    @Operation(summary = "Get followers", description = "Retrieves users that follow a specific user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Followers list retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<Page<FollowResponse>> getFollowers(
            @Parameter(description = "User ID", required = true, example = "1")
            @PathVariable Long userId,
            @Parameter(description = "Page number (0-based)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size", example = "20")
            @RequestParam(defaultValue = "20") int size) {
        
        log.info("Retrieving followers for user ID: {} - page: {}, size: {}", userId, page, size);
        
        Page<FollowResponse> followers = followService.getFollowers(userId, page, size);
        
        log.info("Retrieved {} followers for user ID: {}", followers.getTotalElements(), userId);
        
        return ResponseEntity.ok(followers);
    }

    /**
     * Unfollows a user (deactivates the follow relationship).
     *
     * @param followerId the ID of the user who wants to unfollow
     * @param followingId the ID of the user to unfollow
     * @return success response
     */
    @DeleteMapping("/{followerId}/{followingId}")
    @Operation(summary = "Unfollow a user", description = "Removes a follow relationship between two users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Unfollowed successfully"),
            @ApiResponse(responseCode = "404", description = "Follow relationship not found")
    })
    public ResponseEntity<Void> unfollow(
            @Parameter(description = "Follower user ID", required = true, example = "1")
            @PathVariable Long followerId,
            @Parameter(description = "Following user ID", required = true, example = "2")
            @PathVariable Long followingId) {
        
        log.info("Unfollowing: {} unfollows {}", followerId, followingId);
        
        followService.unfollow(followerId, followingId);
        
        log.info("Successfully unfollowed: {} unfollows {}", followerId, followingId);
        
        return ResponseEntity.noContent().build();
    }
}
