package scorp.socialmedia.like.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import scorp.socialmedia.like.model.entity.Like;

public interface LikeRepository extends JpaRepository<Like, Integer> {
    @Query(value = "SELECT * FROM likes WHERE user_id = :userId AND post_id = :postId", nativeQuery = true)
    Like likeByUserIdAndPostId(Integer userId, Integer postId);
}
