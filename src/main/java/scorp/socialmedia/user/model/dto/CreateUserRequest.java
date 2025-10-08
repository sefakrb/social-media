package scorp.socialmedia.user.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO for creating a new user.
 * Contains all required and optional fields for user registration.
 */
@Schema(description = "Request DTO for creating a new user")
public record CreateUserRequest(
        @NotBlank(message = "Username is required")
        @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
        @Schema(description = "Unique username", example = "john_doe", minLength = 3, maxLength = 50)
        String username,

        @NotBlank(message = "Email is required")
        @Email(message = "Email should be valid")
        @Size(max = 100, message = "Email must not exceed 100 characters")
        @Schema(description = "User's email address", example = "john.doe@example.com", maxLength = 100)
        String email,

        @NotBlank(message = "Full name is required")
        @Size(max = 100, message = "Full name must not exceed 100 characters")
        @Schema(description = "User's full name", example = "John Doe", maxLength = 100)
        @JsonProperty("full_name")
        String fullName,

        @Size(max = 500, message = "Profile picture URL must not exceed 500 characters")
        @Schema(description = "URL to user's profile picture", example = "https://example.com/profile.jpg", maxLength = 500)
        @JsonProperty("profile_picture")
        String profilePicture,

        @Size(max = 500, message = "Bio must not exceed 500 characters")
        @Schema(description = "User's biography", example = "Software developer passionate about technology", maxLength = 500)
        String bio
) {
}
