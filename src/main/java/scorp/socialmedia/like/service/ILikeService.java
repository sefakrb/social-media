package scorp.socialmedia.like.service;

import org.springframework.data.domain.Page;
import scorp.socialmedia.like.dto.CreateLikeRequest;
import scorp.socialmedia.like.dto.LikeResponse;

public interface ILikeService {
    LikeResponse likePost(CreateLikeRequest createLikeRequest);
    Page<LikeResponse> getAllLikes(int page, int size);
    Page<LikeResponse> getLikesByPost(Long postId, int page, int size);
    Page<LikeResponse> getLikesByUser(Long userId, int page, int size);
    void unlikePost(Long userId, Long postId);
    boolean hasUserLikedPost(Long userId, Long postId);
}
