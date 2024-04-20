package scorp.socialmedia.like.service.implementation;

import org.springframework.stereotype.Service;
import scorp.socialmedia.common.model.mapper.ICommonMapper;
import scorp.socialmedia.like.dto.RequestLikePost;
import scorp.socialmedia.like.dto.ResponseLikePost;
import scorp.socialmedia.like.model.entity.Like;
import scorp.socialmedia.like.model.mapper.ILikeMapper;
import scorp.socialmedia.like.model.repository.LikeRepository;
import scorp.socialmedia.like.service.ILikeService;
import scorp.socialmedia.post.model.entity.Post;
import scorp.socialmedia.post.model.repository.PostRepository;
import scorp.socialmedia.user.model.entity.User;
import scorp.socialmedia.user.model.repository.UserRepository;

import java.util.List;

@Service
public class LikeService implements ILikeService {
    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final ILikeMapper likeMapper;
    private final ICommonMapper commonMapper;

    public LikeService(LikeRepository likeRepository, UserRepository userRepository, PostRepository postRepository, ILikeMapper likeMapper, ICommonMapper commonMapper) {
        this.likeRepository = likeRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.likeMapper = likeMapper;
        this.commonMapper = commonMapper;
    }

    @Override
    public ResponseLikePost likePost(RequestLikePost requestLikePost) {
        Like like = likeMapper.requestLikePostToLike(requestLikePost);
        commonMapper.setCreatedAt(like);
        likeRepository.save(like);
        return likeMapper.likeToResponseLikePost(like);
    }

    @Override
    public List<Like> geLikedPosts() {
        return likeRepository.findAll();
    }
}
