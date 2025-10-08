package scorp.socialmedia.user.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import scorp.socialmedia.user.model.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT * FROM users WHERE id IN :ownerIds", nativeQuery = true)
    List<User> findUsersByPostIds(List<Long> ownerIds);
    
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
}
