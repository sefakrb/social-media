package scorp.socialmedia.post.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

/**
 * DTO for post mixing request.
 * Contains post information needed for the mixing algorithm.
 */
@Schema(description = "Request DTO for mixing posts")
public record MixPostRequest(
        @NotNull(message = "Post ID is required")
        @Schema(description = "Post ID", example = "1")
        Long id,

        @NotNull(message = "Owner ID is required")
        @Schema(description = "ID of the post owner", example = "2")
        @JsonProperty("owner_id")
        Long ownerId
) {
}

