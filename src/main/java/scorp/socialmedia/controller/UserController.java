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
import scorp.socialmedia.user.model.dto.CreateUserRequest;
import scorp.socialmedia.user.model.dto.CreateUserResponse;
import scorp.socialmedia.user.model.dto.UserResponse;
import scorp.socialmedia.user.service.IUserService;

/**
 * REST controller for user management operations.
 * Provides endpoints for creating and retrieving users.
 */
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "User Management", description = "APIs for managing users")
public class UserController {

    private final IUserService userService;

    /**
     * Creates a new user.
     *
     * @param request the user creation request
     * @return the created user information
     */
    @PostMapping
    @Operation(summary = "Create a new user", description = "Creates a new user with the provided information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "409", description = "Username or email already exists")
    })
    public ResponseEntity<CreateUserResponse> createUser(
            @Valid @RequestBody CreateUserRequest request) {
        
        log.info("Creating new user with username: {}", request.username());
        
        CreateUserResponse response = userService.createUser(request);
        
        log.info("User created successfully with ID: {}", response.id());
        
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Retrieves a user by ID.
     *
     * @param id the user ID
     * @return the user information
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID", description = "Retrieves user information by user ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<UserResponse> getUser(
            @Parameter(description = "User ID", required = true, example = "1")
            @PathVariable Long id) {
        
        log.info("Retrieving user with ID: {}", id);
        
        UserResponse user = userService.getUserById(id);
        
        if (user == null) {
            log.warn("User not found with ID: {}", id);
            throw new scorp.socialmedia.exception.ResourceNotFoundException("User not found with ID: " + id);
        }
        
        log.info("User retrieved successfully: {}", user.username());
        
        return ResponseEntity.ok(user);
    }

    /**
     * Retrieves all users with pagination.
     *
     * @param page the page number (0-based)
     * @param size the page size
     * @return paginated list of users
     */
    @GetMapping
    @Operation(summary = "Get all users", description = "Retrieves a paginated list of all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users retrieved successfully")
    })
    public ResponseEntity<Page<UserResponse>> getAllUsers(
            @Parameter(description = "Page number (0-based)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size", example = "20")
            @RequestParam(defaultValue = "20") int size) {
        
        log.info("Retrieving users - page: {}, size: {}", page, size);
        
        Page<UserResponse> users = userService.getAllUsers(page, size);
        
        log.info("Retrieved {} users", users.getTotalElements());
        
        return ResponseEntity.ok(users);
    }
}
