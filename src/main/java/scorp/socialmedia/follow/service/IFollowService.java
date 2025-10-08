package scorp.socialmedia.follow.service;

import org.springframework.data.domain.Page;
import scorp.socialmedia.follow.model.dto.CreateFollowRequest;
import scorp.socialmedia.follow.model.dto.FollowResponse;

public interface IFollowService {
    FollowResponse createFollow(CreateFollowRequest createFollowRequest);
    Page<FollowResponse> getAllFollows(int page, int size);
    Page<FollowResponse> getFollowing(Long userId, int page, int size);
    Page<FollowResponse> getFollowers(Long userId, int page, int size);
    void unfollow(Long followerId, Long followingId);
}
