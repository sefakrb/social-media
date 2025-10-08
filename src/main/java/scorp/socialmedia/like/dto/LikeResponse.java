package scorp.socialmedia.like.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO for like relationship response.
 * Contains the like relationship information.
 */
@Schema(description = "Response DTO for like relationship")
public record LikeResponse(
        @Schema(description = "Like relationship ID", example = "1")
        Long id,

        @Schema(description = "ID of the user who liked", example = "1")
        @JsonProperty("user_id")
        Long userId,

        @Schema(description = "ID of the post that was liked", example = "1")
        @JsonProperty("post_id")
        Long postId,

        @Schema(description = "Timestamp when like was created", example = "1640995200")
        @JsonProperty("created_at")
        Long createdAt
) {
}
