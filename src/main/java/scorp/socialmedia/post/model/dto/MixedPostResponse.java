package scorp.socialmedia.post.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO for mixed post response.
 * Contains post information after mixing algorithm is applied.
 */
@Schema(description = "Response DTO for mixed post")
public record MixedPostResponse(
        @Schema(description = "Post ID", example = "1")
        Long id,

        @Schema(description = "ID of the post owner", example = "2")
        @JsonProperty("owner_id")
        Long ownerId
) {
}

