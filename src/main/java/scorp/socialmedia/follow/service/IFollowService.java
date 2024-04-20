package scorp.socialmedia.follow.service;

import scorp.socialmedia.follow.model.dto.RequestFollow;
import scorp.socialmedia.follow.model.dto.ResponseFollow;
import scorp.socialmedia.follow.model.entity.Follow;

import java.util.List;

public interface IFollowService {
    ResponseFollow follow(RequestFollow requestFollow);

    List<Follow> getFollows();
}
