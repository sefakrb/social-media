package scorp.socialmedia.like.service;

import scorp.socialmedia.like.dto.RequestLikePost;
import scorp.socialmedia.like.dto.ResponseLikePost;
import scorp.socialmedia.like.model.entity.Like;

import java.util.List;

public interface ILikeService {
    ResponseLikePost likePost(RequestLikePost requestLikePost);

    List<Like> geLikedPosts();
}
