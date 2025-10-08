package scorp.socialmedia.follow.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

/**
 * DTO for creating a follow relationship.
 * Contains the follower and following user IDs.
 */
@Schema(description = "Request DTO for creating a follow relationship")
public record CreateFollowRequest(
        @NotNull(message = "Follower ID is required")
        @Schema(description = "ID of the user who is following", example = "1")
        @JsonProperty("follower_id")
        Long followerId,

        @NotNull(message = "Following ID is required")
        @Schema(description = "ID of the user being followed", example = "2")
        @JsonProperty("following_id")
        Long followingId
) {
}
