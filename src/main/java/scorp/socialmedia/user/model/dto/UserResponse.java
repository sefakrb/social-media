package scorp.socialmedia.user.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO for user response.
 * Contains complete user information for API responses.
 */
@Schema(description = "Complete user information response")
public record UserResponse(
        @Schema(description = "User's unique identifier", example = "1")
        Long id,

        @Schema(description = "User's username", example = "john_doe")
        String username,

        @Schema(description = "User's email address", example = "john.doe@example.com")
        String email,

        @Schema(description = "User's full name", example = "John Doe")
        @JsonProperty("full_name")
        String fullName,

        @Schema(description = "URL to user's profile picture", example = "https://example.com/profile.jpg")
        @JsonProperty("profile_picture")
        String profilePicture,

        @Schema(description = "User's biography", example = "Software developer passionate about technology")
        String bio,

        @Schema(description = "User's account status", example = "true")
        @JsonProperty("is_active")
        Boolean isActive,

        @Schema(description = "Timestamp when user was created", example = "1640995200")
        @JsonProperty("created_at")
        Long createdAt
) {
}

