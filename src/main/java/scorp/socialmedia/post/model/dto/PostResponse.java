package scorp.socialmedia.post.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO for post response.
 * Contains post information with user details and interaction status.
 */
@Schema(description = "Response DTO for post information")
public record PostResponse(
        @Schema(description = "Post ID", example = "1")
        Long id,

        @Schema(description = "Post description", example = "Just had an amazing day at the beach! 🌊")
        String description,

        @Schema(description = "Post owner information")
        PostUserDto owner,

        @Schema(description = "Post image URL", example = "https://example.com/post-image.jpg")
        @JsonProperty("image_url")
        String imageUrl,

        @Schema(description = "Timestamp when post was created", example = "1640995200")
        @JsonProperty("created_at")
        Long createdAt,

        @Schema(description = "Whether the current user liked this post", example = "true")
        Boolean liked
) {
}

