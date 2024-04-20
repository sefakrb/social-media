package scorp.socialmedia.follow.service.implementation;

import org.springframework.stereotype.Service;
import scorp.socialmedia.common.model.mapper.ICommonMapper;
import scorp.socialmedia.follow.model.dto.RequestFollow;
import scorp.socialmedia.follow.model.dto.ResponseFollow;
import scorp.socialmedia.follow.model.entity.Follow;
import scorp.socialmedia.follow.model.mapper.IFollowMapper;
import scorp.socialmedia.follow.model.repository.FollowRepository;
import scorp.socialmedia.follow.service.IFollowService;
import scorp.socialmedia.user.model.entity.User;
import scorp.socialmedia.user.model.repository.UserRepository;

import java.util.List;

@Service
public class FollowService implements IFollowService {
    private final UserRepository userRepository;
    private final FollowRepository followRepository;
    private final IFollowMapper followMapper;
    private final ICommonMapper commonMapper;

    public FollowService(UserRepository userRepository, FollowRepository followRepository, IFollowMapper followMapper, ICommonMapper commonMapper) {
        this.userRepository = userRepository;
        this.followRepository = followRepository;
        this.followMapper = followMapper;
        this.commonMapper = commonMapper;
    }

    @Override
    public ResponseFollow follow(RequestFollow requestFollow) {
        Follow follow = followMapper.requestFollowToFollow(requestFollow);
        commonMapper.setCreatedAt(follow);
        followRepository.save(follow);
        return followMapper.followToResponseFollow(follow);
    }

    @Override
    public List<Follow> getFollows() {
        return followRepository.findAll();
    }
}
