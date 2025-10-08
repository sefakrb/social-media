package scorp.socialmedia.like.service.implementation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import scorp.socialmedia.common.model.mapper.ICommonMapper;
import scorp.socialmedia.like.dto.CreateLikeRequest;
import scorp.socialmedia.like.dto.LikeResponse;
import scorp.socialmedia.like.model.entity.Like;
import scorp.socialmedia.like.model.mapper.ILikeMapper;
import scorp.socialmedia.like.model.repository.LikeRepository;
import scorp.socialmedia.like.service.ILikeService;

@Service
public class LikeService implements ILikeService {
    private final LikeRepository likeRepository;
    private final ILikeMapper likeMapper;
    private final ICommonMapper commonMapper;

    public LikeService(LikeRepository likeRepository, ILikeMapper likeMapper, ICommonMapper commonMapper) {
        this.likeRepository = likeRepository;
        this.likeMapper = likeMapper;
        this.commonMapper = commonMapper;
    }

    @Override
    public LikeResponse likePost(CreateLikeRequest createLikeRequest) {
        Like like = likeMapper.requestLikePostToLike(createLikeRequest);
        commonMapper.setCreatedAt(like);
        likeRepository.save(like);
        return likeMapper.likeToResponseLikePost(like);
    }

    @Override
    public Page<LikeResponse> getAllLikes(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Like> likePage = likeRepository.findAll(pageable);
        return likePage.map(likeMapper::likeToResponseLikePost);
    }

    @Override
    public Page<LikeResponse> getLikesByPost(Long postId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Like> likePage = likeRepository.findByPostId(postId, pageable);
        return likePage.map(likeMapper::likeToResponseLikePost);
    }

    @Override
    public Page<LikeResponse> getLikesByUser(Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Like> likePage = likeRepository.findByUserId(userId, pageable);
        return likePage.map(likeMapper::likeToResponseLikePost);
    }

    @Override
    public void unlikePost(Long userId, Long postId) {
        likeRepository.deleteByUserIdAndPostId(userId, postId);
    }

    @Override
    public boolean hasUserLikedPost(Long userId, Long postId) {
        return likeRepository.existsByUserIdAndPostId(userId, postId);
    }
}
