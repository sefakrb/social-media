package scorp.socialmedia.follow.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import scorp.socialmedia.follow.model.entity.Follow;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    @Query(value = "SELECT * FROM follow WHERE follower_id = :userId AND following_id IN :postOwnersList", nativeQuery = true)
    List<Follow> findFollowsByUserIdAndOwnerIds(Long userId, List<Long> postOwnersList);
    
    @Query("SELECT f FROM Follow f WHERE f.follower.id = :followerId")
    Page<Follow> findByFollowerId(Long followerId, Pageable pageable);
    
    @Query("SELECT f FROM Follow f WHERE f.following.id = :followingId")
    Page<Follow> findByFollowingId(Long followingId, Pageable pageable);
    
    @Modifying
    @Query("DELETE FROM Follow f WHERE f.follower.id = :followerId AND f.following.id = :followingId")
    int deleteByFollowerIdAndFollowingId(Long followerId, Long followingId);
}
