package scorp.socialmedia.follow.service.implementation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import scorp.socialmedia.common.model.mapper.ICommonMapper;
import scorp.socialmedia.follow.model.dto.CreateFollowRequest;
import scorp.socialmedia.follow.model.dto.FollowResponse;
import scorp.socialmedia.follow.model.entity.Follow;
import scorp.socialmedia.follow.model.mapper.IFollowMapper;
import scorp.socialmedia.follow.model.repository.FollowRepository;
import scorp.socialmedia.follow.service.IFollowService;

@Service
public class FollowService implements IFollowService {
    private final FollowRepository followRepository;
    private final IFollowMapper followMapper;
    private final ICommonMapper commonMapper;

    public FollowService(FollowRepository followRepository, IFollowMapper followMapper, ICommonMapper commonMapper) {
        this.followRepository = followRepository;
        this.followMapper = followMapper;
        this.commonMapper = commonMapper;
    }

    @Override
    public FollowResponse createFollow(CreateFollowRequest createFollowRequest) {
        Follow follow = followMapper.requestFollowToFollow(createFollowRequest);
        commonMapper.setCreatedAt(follow);
        followRepository.save(follow);
        return followMapper.followToResponseFollow(follow);
    }

    @Override
    public Page<FollowResponse> getAllFollows(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Follow> followPage = followRepository.findAll(pageable);
        return followPage.map(followMapper::followToResponseFollow);
    }

    @Override
    public Page<FollowResponse> getFollowing(Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Follow> followPage = followRepository.findByFollowerId(userId, pageable);
        return followPage.map(followMapper::followToResponseFollow);
    }

    @Override
    public Page<FollowResponse> getFollowers(Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Follow> followPage = followRepository.findByFollowingId(userId, pageable);
        return followPage.map(followMapper::followToResponseFollow);
    }

    @Override
    public void unfollow(Long followerId, Long followingId) {
        followRepository.deleteByFollowerIdAndFollowingId(followerId, followingId);
    }
}
