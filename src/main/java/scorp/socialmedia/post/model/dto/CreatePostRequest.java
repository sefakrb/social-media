package scorp.socialmedia.post.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * DTO for creating a new post.
 * Contains post content and metadata.
 */
@Schema(description = "Request DTO for creating a new post")
public record CreatePostRequest(
        @NotBlank(message = "Description is required")
        @Size(max = 2000, message = "Description must not exceed 2000 characters")
        @Schema(description = "Post content/description", example = "Just had an amazing day at the beach! 🌊", maxLength = 2000)
        String description,

        @NotNull(message = "User ID is required")
        @Schema(description = "ID of the user creating the post", example = "1")
        @JsonProperty("user_id")
        Long userId,

        @Size(max = 500, message = "Image URL must not exceed 500 characters")
        @Schema(description = "URL to post image", example = "https://example.com/post-image.jpg", maxLength = 500)
        @JsonProperty("image_url")
        String imageUrl
) {
}
