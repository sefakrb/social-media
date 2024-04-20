package scorp.socialmedia.follow.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import scorp.socialmedia.follow.model.entity.Follow;

public interface FollowRepository extends JpaRepository<Follow,Integer> {
    @Query(value = "SELECT * FROM follow WHERE follower_id = :userId AND following_id = :ownerId", nativeQuery = true)
    Follow followByUserIdAndOwnerId(Integer userId, Integer ownerId);
}
