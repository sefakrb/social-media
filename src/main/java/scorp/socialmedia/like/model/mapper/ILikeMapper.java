package scorp.socialmedia.like.model.mapper;

import scorp.socialmedia.like.dto.RequestLikePost;
import scorp.socialmedia.like.dto.ResponseLikePost;
import scorp.socialmedia.like.model.entity.Like;
import scorp.socialmedia.post.model.entity.Post;
import scorp.socialmedia.user.model.entity.User;

public interface ILikeMapper {
    Like requestLikePostToLike(RequestLikePost requestLikePost);

    ResponseLikePost likeToResponseLikePost(Like like);
}
