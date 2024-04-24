package scorp.socialmedia.follow.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import scorp.socialmedia.follow.model.entity.Follow;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Integer> {
    @Query(value = "SELECT * FROM follow WHERE follower_id = :userId AND following_id IN :postOwnersList", nativeQuery = true)
    List<Follow> findFollowsByUserIdAndOwnerIds(Integer userId, List<Integer> postOwnersList);
}
