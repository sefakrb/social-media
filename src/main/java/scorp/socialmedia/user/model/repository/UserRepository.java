package scorp.socialmedia.user.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import scorp.socialmedia.user.model.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query(value = "SELECT * FROM users WHERE id = :id", nativeQuery = true)
    User findCustomById(Integer id);
}
