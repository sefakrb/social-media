package scorp.socialmedia.follow.model.mapper;

import scorp.socialmedia.follow.model.dto.RequestFollow;
import scorp.socialmedia.follow.model.dto.ResponseFollow;
import scorp.socialmedia.follow.model.entity.Follow;
import scorp.socialmedia.user.model.entity.User;

public interface IFollowMapper {
    Follow requestFollowToFollow(RequestFollow requestFollow);

    ResponseFollow followToResponseFollow(Follow follow);
}
