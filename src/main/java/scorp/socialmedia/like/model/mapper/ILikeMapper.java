package scorp.socialmedia.like.model.mapper;

import scorp.socialmedia.like.dto.CreateLikeRequest;
import scorp.socialmedia.like.dto.LikeResponse;
import scorp.socialmedia.like.model.entity.Like;
import scorp.socialmedia.post.model.entity.Post;
import scorp.socialmedia.user.model.entity.User;

public interface ILikeMapper {
    Like requestLikePostToLike(CreateLikeRequest createLikeRequest);

    LikeResponse likeToResponseLikePost(Like like);
}
