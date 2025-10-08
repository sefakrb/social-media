package scorp.socialmedia.follow.model.mapper.implementation;

import org.springframework.stereotype.Component;
import scorp.socialmedia.follow.model.dto.CreateFollowRequest;
import scorp.socialmedia.follow.model.dto.FollowResponse;
import scorp.socialmedia.follow.model.entity.Follow;
import scorp.socialmedia.follow.model.mapper.IFollowMapper;
import scorp.socialmedia.user.model.entity.User;

@Component
public class FollowMapper implements IFollowMapper {
    @Override
    public Follow requestFollowToFollow(CreateFollowRequest createFollowRequest) {
        Follow follow = new Follow();
        follow.setFollowerId(createFollowRequest.followerId());
        follow.setFollowingId(createFollowRequest.followingId());
        return follow;
    }

    @Override
    public FollowResponse followToResponseFollow(Follow follow) {
        return new FollowResponse(
                follow.getId(),
                follow.getFollowerId(),
                follow.getFollowingId(),
                follow.getCreatedAt()
        );
    }
}
