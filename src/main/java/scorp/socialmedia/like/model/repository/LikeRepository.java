package scorp.socialmedia.like.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import scorp.socialmedia.like.model.entity.Like;

import java.util.List;

public interface LikeRepository extends JpaRepository<Like, Long> {
    @Query(value = "SELECT * FROM likes WHERE user_id = :userId AND post_id IN :postIds", nativeQuery = true)
    List<Like> likesByUserIdAndPostIds(Long userId, List<Long> postIds);
    
    @Query("SELECT l FROM Like l WHERE l.post.id = :postId")
    Page<Like> findByPostId(Long postId, Pageable pageable);
    
    @Query("SELECT l FROM Like l WHERE l.user.id = :userId")
    Page<Like> findByUserId(Long userId, Pageable pageable);
    
    @Modifying
    @Query("DELETE FROM Like l WHERE l.user.id = :userId AND l.post.id = :postId")
    int deleteByUserIdAndPostId(Long userId, Long postId);
    
    @Query("SELECT COUNT(l) > 0 FROM Like l WHERE l.user.id = :userId AND l.post.id = :postId")
    boolean existsByUserIdAndPostId(Long userId, Long postId);
}
