package scorp.socialmedia.like.model.mapper.implementation;

import org.springframework.stereotype.Component;
import scorp.socialmedia.like.dto.RequestLikePost;
import scorp.socialmedia.like.dto.ResponseLikePost;
import scorp.socialmedia.like.model.entity.Like;
import scorp.socialmedia.like.model.mapper.ILikeMapper;

@Component
public class LikeMapper implements ILikeMapper {
    @Override
    public Like requestLikePostToLike(RequestLikePost requestLikePost) {
        Like like = new Like();
        like.setUser_id(requestLikePost.getUser_id());
        like.setPost_id(requestLikePost.getPost_id());
        return like;
    }

    @Override
    public ResponseLikePost likeToResponseLikePost(Like like) {
        ResponseLikePost responseLikePost = new ResponseLikePost();
        responseLikePost.setPost_id(like.getPost_id());
        responseLikePost.setUser_id(like.getUser_id());
        return responseLikePost;
    }
}
