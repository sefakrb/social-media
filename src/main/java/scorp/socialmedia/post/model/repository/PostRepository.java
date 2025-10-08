package scorp.socialmedia.post.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import scorp.socialmedia.post.model.entity.Post;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(value = "SELECT * FROM posts WHERE id IN :postIds", nativeQuery = true)
    List<Post> postsById(List<Long> postIds);
    
    Page<Post> findByUserId(Long userId, Pageable pageable);
}
