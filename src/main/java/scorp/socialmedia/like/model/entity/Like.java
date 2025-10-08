package scorp.socialmedia.like.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import scorp.socialmedia.common.model.entity.BaseModel;
import scorp.socialmedia.post.model.entity.Post;
import scorp.socialmedia.user.model.entity.User;

/**
 * Like entity representing a like relationship between users and posts.
 * Tracks which users have liked which posts.
 */
@Entity
@Table(name = "likes", 
       uniqueConstraints = {
           @UniqueConstraint(columnNames = {"user_id", "post_id"})
       })
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Like extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "User is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull(message = "Post is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Column(name = "is_active", nullable = false)
    @Builder.Default
    private Boolean isActive = true;

    // Helper methods
    public Long getUserId() {
        return user != null ? user.getId() : null;
    }

    public void setUserId(Long userId) {
        if (user == null) {
            user = new User();
        }
        user.setId(userId);
    }

    public Long getPostId() {
        return post != null ? post.getId() : null;
    }

    public void setPostId(Long postId) {
        if (post == null) {
            post = new Post();
        }
        post.setId(postId);
    }
}
