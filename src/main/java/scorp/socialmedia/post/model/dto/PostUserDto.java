package scorp.socialmedia.post.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO for user information in post responses.
 * Contains user details and follow status.
 */
@Schema(description = "User information DTO for post responses")
public record PostUserDto(
        @Schema(description = "User ID", example = "1")
        Long id,

        @Schema(description = "Username", example = "john_doe")
        String username,

        @Schema(description = "User's full name", example = "John Doe")
        @JsonProperty("full_name")
        String fullName,

        @Schema(description = "URL to user's profile picture", example = "https://example.com/profile.jpg")
        @JsonProperty("profile_picture")
        String profilePicture,

        @Schema(description = "Whether the current user follows this user", example = "true")
        Boolean followed
) {
}

