package scorp.socialmedia.follow.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import scorp.socialmedia.common.model.entity.BaseModel;
import scorp.socialmedia.user.model.entity.User;

/**
 * Follow entity representing a follow relationship between users.
 * Tracks who follows whom in the social media application.
 */
@Entity
@Table(name = "follows", 
       uniqueConstraints = {
           @UniqueConstraint(columnNames = {"follower_id", "following_id"})
       })
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Follow extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Follower is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follower_id", nullable = false)
    private User follower;

    @NotNull(message = "Following user is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "following_id", nullable = false)
    private User following;

    @Column(name = "is_active", nullable = false)
    @Builder.Default
    private Boolean isActive = true;

    // Helper methods
    public Long getFollowerId() {
        return follower != null ? follower.getId() : null;
    }

    public void setFollowerId(Long followerId) {
        if (follower == null) {
            follower = new User();
        }
        follower.setId(followerId);
    }

    public Long getFollowingId() {
        return following != null ? following.getId() : null;
    }

    public void setFollowingId(Long followingId) {
        if (following == null) {
            following = new User();
        }
        following.setId(followingId);
    }
}
