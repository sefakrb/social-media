package scorp.socialmedia.like.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

/**
 * DTO for creating a like relationship.
 * Contains the user and post IDs for the like.
 */
@Schema(description = "Request DTO for creating a like relationship")
public record CreateLikeRequest(
        @NotNull(message = "User ID is required")
        @Schema(description = "ID of the user who is liking", example = "1")
        @JsonProperty("user_id")
        Long userId,

        @NotNull(message = "Post ID is required")
        @Schema(description = "ID of the post being liked", example = "1")
        @JsonProperty("post_id")
        Long postId
) {
}
