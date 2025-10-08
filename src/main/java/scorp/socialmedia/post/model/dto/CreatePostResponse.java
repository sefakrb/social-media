package scorp.socialmedia.post.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO for post creation response.
 * Contains the created post's basic information.
 */
@Schema(description = "Response DTO for post creation")
public record CreatePostResponse(
        @Schema(description = "Created post's ID", example = "1")
        Long id,

        @Schema(description = "ID of the user who created the post", example = "1")
        @JsonProperty("user_id")
        Long userId,

        @Schema(description = "Post description", example = "Just had an amazing day at the beach! 🌊")
        String description,

        @Schema(description = "URL to post image", example = "https://example.com/post-image.jpg")
        @JsonProperty("image_url")
        String imageUrl,

        @Schema(description = "Timestamp when post was created", example = "1640995200")
        @JsonProperty("created_at")
        Long createdAt
) {
}
