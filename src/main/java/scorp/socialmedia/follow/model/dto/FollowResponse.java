package scorp.socialmedia.follow.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO for follow relationship response.
 * Contains the follow relationship information.
 */
@Schema(description = "Response DTO for follow relationship")
public record FollowResponse(
        @Schema(description = "Follow relationship ID", example = "1")
        Long id,

        @Schema(description = "ID of the user who is following", example = "1")
        @JsonProperty("follower_id")
        Long followerId,

        @Schema(description = "ID of the user being followed", example = "2")
        @JsonProperty("following_id")
        Long followingId,

        @Schema(description = "Timestamp when follow relationship was created", example = "1640995200")
        @JsonProperty("created_at")
        Long createdAt
) {
}
