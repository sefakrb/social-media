package scorp.socialmedia.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import scorp.socialmedia.user.model.dto.CreateUserRequest;
import scorp.socialmedia.user.model.dto.CreateUserResponse;
import scorp.socialmedia.user.model.dto.UserResponse;
import scorp.socialmedia.user.service.IUserService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Unit tests for UserController.
 * Tests the REST API endpoints for user management.
 */
@WebMvcTest(controllers = UserController.class)
@org.springframework.context.annotation.Import(scorp.socialmedia.exception.GlobalExceptionHandler.class)
@DisplayName("User Controller Tests")
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private IUserService userService;

    @Test
    @DisplayName("Should create user successfully")
    void shouldCreateUserSuccessfully() throws Exception {
        // Given
        CreateUserRequest request = new CreateUserRequest(
                "john_doe",
                "john.doe@example.com",
                "John Doe",
                "https://example.com/profile.jpg",
                "Software developer"
        );

        CreateUserResponse response = new CreateUserResponse(
                1L,
                "john_doe",
                "john.doe@example.com",
                "John Doe"
        );

        when(userService.createUser(any(CreateUserRequest.class))).thenReturn(response);

        // When & Then
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value("john_doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"))
                .andExpect(jsonPath("$.full_name").value("John Doe"));
    }

    @Test
    @DisplayName("Should return validation error for invalid user data")
    void shouldReturnValidationErrorForInvalidUserData() throws Exception {
        // Given
        CreateUserRequest request = new CreateUserRequest(
                "", // Invalid: empty username
                "invalid-email", // Invalid: malformed email
                "", // Invalid: empty full name
                null,
                null
        );

        // When & Then
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Validation Failed"));
    }

    @Test
    @DisplayName("Should get user by ID successfully")
    void shouldGetUserByIdSuccessfully() throws Exception {
        // Given
        Long userId = 1L;
        UserResponse userResponse = new UserResponse(
                userId,
                "john_doe",
                "john.doe@example.com",
                "John Doe",
                "https://example.com/profile.jpg",
                "Software developer",
                true,
                1640995200L
        );

        when(userService.getUserById(userId)).thenReturn(userResponse);

        // When & Then
        mockMvc.perform(get("/users/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value("john_doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"))
                .andExpect(jsonPath("$.full_name").value("John Doe"))
                .andExpect(jsonPath("$.is_active").value(true));
    }

    @Test
    @DisplayName("Should return 404 when user not found")
    void shouldReturn404WhenUserNotFound() throws Exception {
        // Given
        Long userId = 999L;
        when(userService.getUserById(userId)).thenThrow(new scorp.socialmedia.exception.ResourceNotFoundException("User not found"));

        // When & Then
        mockMvc.perform(get("/users/{id}", userId))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Resource Not Found"))
                .andExpect(jsonPath("$.message").value("User not found"));
    }
}

