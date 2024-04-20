package scorp.socialmedia.follow.model.mapper.implementation;

import org.springframework.stereotype.Component;
import scorp.socialmedia.follow.model.dto.RequestFollow;
import scorp.socialmedia.follow.model.dto.ResponseFollow;
import scorp.socialmedia.follow.model.entity.Follow;
import scorp.socialmedia.follow.model.mapper.IFollowMapper;
import scorp.socialmedia.user.model.entity.User;

@Component
public class FollowMapper implements IFollowMapper {
    @Override
    public Follow requestFollowToFollow(RequestFollow requestFollow) {
        Follow follow = new Follow();
        follow.setFollower_id(requestFollow.getFollower_id());
        follow.setFollowing_id(requestFollow.getFollowing_id());
        return follow;
    }

    @Override
    public ResponseFollow followToResponseFollow(Follow follow) {
        ResponseFollow responseFollow = new ResponseFollow();
        responseFollow.setFollower_id(follow.getFollower_id());
        responseFollow.setFollowing_id(follow.getFollowing_id());
        return responseFollow;
    }
}
