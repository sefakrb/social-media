package scorp.socialmedia.user.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO for user creation response.
 * Contains the created user's basic information.
 */
@Schema(description = "Response DTO for user creation")
public record CreateUserResponse(
        @Schema(description = "Created user's ID", example = "1")
        Long id,

        @Schema(description = "Created user's username", example = "john_doe")
        @JsonProperty("username")
        String username,

        @Schema(description = "Created user's email", example = "john.doe@example.com")
        String email,

        @Schema(description = "Created user's full name", example = "John Doe")
        @JsonProperty("full_name")
        String fullName
) {
}
