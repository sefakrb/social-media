package scorp.socialmedia.like.model.mapper.implementation;

import org.springframework.stereotype.Component;
import scorp.socialmedia.like.dto.CreateLikeRequest;
import scorp.socialmedia.like.dto.LikeResponse;
import scorp.socialmedia.like.model.entity.Like;
import scorp.socialmedia.like.model.mapper.ILikeMapper;

@Component
public class LikeMapper implements ILikeMapper {
    @Override
    public Like requestLikePostToLike(CreateLikeRequest createLikeRequest) {
        Like like = new Like();
        like.setUserId(createLikeRequest.userId());
        like.setPostId(createLikeRequest.postId());
        return like;
    }

    @Override
    public LikeResponse likeToResponseLikePost(Like like) {
        return new LikeResponse(
                like.getId(),
                like.getUserId(),
                like.getPostId(),
                like.getCreatedAt()
        );
    }
}
