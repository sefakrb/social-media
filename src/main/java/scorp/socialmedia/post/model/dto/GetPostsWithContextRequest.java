package scorp.socialmedia.post.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * DTO for requesting posts with user context.
 * Contains user ID and list of post IDs to retrieve with context.
 */
@Schema(description = "Request DTO for getting posts with user context")
public record GetPostsWithContextRequest(
        @NotNull(message = "User ID is required")
        @Schema(description = "ID of the user requesting posts with context", example = "1")
        @JsonProperty("user_id")
        Long userId,

        @NotEmpty(message = "Post IDs list cannot be empty")
        @Schema(description = "List of post IDs to retrieve with context", example = "[1, 2, 3]")
        @JsonProperty("post_ids")
        List<Long> postIds
) {
}

