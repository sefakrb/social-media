package scorp.socialmedia.follow.model.mapper;

import scorp.socialmedia.follow.model.dto.CreateFollowRequest;
import scorp.socialmedia.follow.model.dto.FollowResponse;
import scorp.socialmedia.follow.model.entity.Follow;
import scorp.socialmedia.user.model.entity.User;

public interface IFollowMapper {
    Follow requestFollowToFollow(CreateFollowRequest createFollowRequest);

    FollowResponse followToResponseFollow(Follow follow);
}
