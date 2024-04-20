package scorp.socialmedia.post.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import scorp.socialmedia.post.model.entity.Post;

public interface PostRepository extends JpaRepository<Post, Integer> {

    @Query(value = "SELECT * FROM post WHERE id = :id", nativeQuery = true)
    Post postById(Integer id);
}
