package scorp.socialmedia.post.service.implementation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import scorp.socialmedia.common.model.mapper.ICommonMapper;
import scorp.socialmedia.follow.model.entity.Follow;
import scorp.socialmedia.follow.model.repository.FollowRepository;
import scorp.socialmedia.like.model.entity.Like;
import scorp.socialmedia.like.model.repository.LikeRepository;
import scorp.socialmedia.post.model.dto.*;
import scorp.socialmedia.post.model.entity.Post;
import scorp.socialmedia.post.model.mapper.IPostMapper;
import scorp.socialmedia.post.model.repository.PostRepository;
import scorp.socialmedia.post.service.IPostService;
import scorp.socialmedia.user.model.entity.User;
import scorp.socialmedia.user.model.repository.UserRepository;

import java.util.*;

@Service
public class PostService implements IPostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final FollowRepository followRepository;
    private final LikeRepository likeRepository;
    private final IPostMapper postMapper;
    private final ICommonMapper commonMapper;

    public PostService(PostRepository postRepository, UserRepository userRepository, FollowRepository followRepository, LikeRepository likeRepository, IPostMapper postMapper, ICommonMapper commonMapper) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.followRepository = followRepository;
        this.likeRepository = likeRepository;
        this.postMapper = postMapper;
        this.commonMapper = commonMapper;
    }

    @Override
    public CreatePostResponse createPost(CreatePostRequest createPostRequest) {
        Post newPost = postMapper.requestCreatePostToPost(createPostRequest);
        commonMapper.setCreatedAt(newPost);
        postRepository.save(newPost);
        return postMapper.postToResponseCreatePost(newPost);
    }

    @Override
    public Page<PostResponse> getAllPosts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Post> posts = postRepository.findAll(pageable);
        return posts.map(postMapper::postToPostResponse);
    }

    @Override
    public Page<PostResponse> getPostsByUser(Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Post> posts = postRepository.findByUserId(userId, pageable);
        return posts.map(postMapper::postToPostResponse);
    }

    @Override
    public List<PostWithContextResponse> getPostsWithContext(GetPostsWithContextRequest request) {
        List<PostWithContextResponse> responseGetPosts = new ArrayList<>();
        List<Post> existingPostsList = postRepository.postsById(request.postIds());
        List<Like> requestedUserLikes = likeRepository.likesByUserIdAndPostIds(request.userId(), request.postIds());
        HashMap<Long, Post> existingPostsMap = new HashMap<>();
        List<Long> postOwnerIds = new ArrayList<>();

        for (Post post : existingPostsList) {
            existingPostsMap.putIfAbsent(post.getId(), post);
            postOwnerIds.add(post.getUserId());
        }

        List<User> postOwnersList = userRepository.findUsersByPostIds(postOwnerIds);
        List<Follow> requestedUserFollowList = followRepository.findFollowsByUserIdAndOwnerIds(request.userId(), postOwnerIds);

        for (int i = 0; i < request.postIds().size(); i++) {
            if (Objects.isNull(existingPostsMap.get(request.postIds().get(i)))) {
                responseGetPosts.add(null);
            } else {
                Post post = existingPostsMap.get(request.postIds().get(i));
                Boolean controlRequestedUserLike = controlRequestedUserLike(requestedUserLikes, post.getId());
                PostUserDto postUserDto = addOwnerToPostUserDto(postOwnersList, requestedUserFollowList, post);

                if (postUserDto == null) {
                    responseGetPosts.add(null);
                } else {
                    PostWithContextResponse postWithContext = new PostWithContextResponse(
                        post.getId(),
                        post.getDescription(),
                        postUserDto,
                        post.getImageUrl(),
                        post.getCreatedAt(),
                        controlRequestedUserLike,
                        postUserDto.followed()
                    );

                    responseGetPosts.add(postWithContext);
                }
            }
        }
        return responseGetPosts;
    }

    @Override
    public List<MixedPostResponse> mixPostsByOwners(List<MixPostRequest> request) {
        HashMap<Long, CountDto> counter = new HashMap<>();
        MixPostRequest[] responseList = new MixPostRequest[request.size()];

        for (int i = 0; i < request.size(); i++) {
            MixPostRequest currentItem = request.get(i);

            if (counter.containsKey(currentItem.ownerId())) {
                counter.get(currentItem.ownerId()).setPostCount(counter.get(currentItem.ownerId()).getPostCount() + 1);
            } else {
                CountDto countDto = new CountDto();
                countDto.setPostCount(1);
                countDto.setStartIndex(i);
                counter.put(currentItem.ownerId(), countDto);
            }
        }

        Long[] uniqueKeys = counter.keySet().toArray(new Long[0]);
        int uniqueKeysLength = uniqueKeys.length;
        int loopTimes = request.size();
        int lastAddedIndex = 0;

        for (int i = 0; i < loopTimes; i++) {
            CountDto currentKey = counter.get(uniqueKeys[i % uniqueKeysLength]);
            if (currentKey.getPostCount() > 0) {
                responseList[lastAddedIndex] = request.get(currentKey.getStartIndex());
                lastAddedIndex = lastAddedIndex + 1;
                currentKey.setStartIndex(currentKey.getStartIndex() + 1);
                currentKey.setPostCount(currentKey.getPostCount() - 1);
            } else {
                loopTimes = loopTimes + 1;
            }
        }
        
        List<MixedPostResponse> mixedResponses = new ArrayList<>();
        for (MixPostRequest mixRequest : responseList) {
            if (mixRequest != null) {
                mixedResponses.add(new MixedPostResponse(mixRequest.id(), mixRequest.ownerId()));
            }
        }
        return mixedResponses;
    }

    private PostUserDto addOwnerToPostUserDto(List<User> postOwnersList, List<Follow> requestedUserFollowList, Post post) {
        boolean isFollow = false;

        for (Follow follow : requestedUserFollowList) {
            if (post.getUserId().equals(follow.getFollowingId())) {
                isFollow = true;
                break;
            }
        }

        for (User user : postOwnersList) {
            if (post.getUserId().equals(user.getId())) {
                return new PostUserDto(
                    user.getId(),
                    user.getUsername(),
                    user.getFullName(),
                    user.getProfilePicture(),
                    isFollow
                );
            }
        }
        return null;
    }

    private Boolean controlRequestedUserLike(List<Like> requestedUserLikes, Long postId) {
        for (Like like : requestedUserLikes) {
            if (Objects.equals(like.getPostId(), postId)) {
                return true;
            }
        }
        return false;
    }
}

