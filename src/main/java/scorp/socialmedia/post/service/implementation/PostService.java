package scorp.socialmedia.post.service.implementation;

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
    public ResponseCreatePost createPost(RequestCreatePost requestCreatePost) {
        Post newPost = postMapper.requestCreatePostToPost(requestCreatePost);
        commonMapper.setCreatedAt(newPost);
        postRepository.save(newPost);
        return postMapper.postToResponseCreatePost(newPost);
    }

    @Override
    public List<Post> getPosts() {
        return postRepository.findAll();
    }

    @Override
    public List<ResponseGetPosts> get_posts(RequestGetPosts requestGetPosts) {
        List<ResponseGetPosts> responseGetPosts = new ArrayList<>();
        for (int i = 0; i < requestGetPosts.getPost_ids().size(); i++) {
            Post post = postRepository.postById(requestGetPosts.getPost_ids().get(i));

            if (Objects.nonNull(post)) {
                ResponseGetPosts getPosts = new ResponseGetPosts();
                getPosts.setId(post.getId());
                getPosts.setDescription(post.getDescription());
                getPosts.setImage(post.getImage());
                getPosts.setCreated_at(post.getCreated_at());
                Boolean controlRequestedUserLike = controlRequestedUserLike(requestGetPosts.getUser_id(), post);
                getPosts.setLiked(controlRequestedUserLike);
                GetPostsUserDto getPostsUserDto = addOwner(requestGetPosts.getUser_id(), post.getUser_id());
                getPosts.setOwner(getPostsUserDto);

                responseGetPosts.add(getPosts);
            } else {
                responseGetPosts.add(null);
            }
        }
        return responseGetPosts;
    }

    @Override
    public List<ResponseMixByOwners> mix_by_owners(List<RequestMixByOwners> requestMixByOwners) {
        HashMap<Integer, CountDto> counter = new HashMap<>();

        RequestMixByOwners[] responseList = new RequestMixByOwners[requestMixByOwners.size()];

        for (int i = 0; i < requestMixByOwners.size(); i++) {
            RequestMixByOwners currentItem = requestMixByOwners.get(i);

            if (counter.containsKey(currentItem.getOwner_id())) {
                counter.get(currentItem.getOwner_id()).setPostCount(counter.get(currentItem.getOwner_id()).getPostCount() + 1);
            } else {
                CountDto countDto = new CountDto();
                countDto.setPostCount(1);
                countDto.setStartIndex(i);
                counter.put(currentItem.getOwner_id(), countDto);
            }
        }

        Integer[] uniqueKeys = counter.keySet().toArray(new Integer[0]);
        int uniqueKeysLength = uniqueKeys.length;

        for (int i = 0; i <= requestMixByOwners.size(); i++) {
            CountDto currentKey = counter.get(uniqueKeys[i % uniqueKeysLength]);

            if (currentKey.getPostCount() > 0) {
                if (i == requestMixByOwners.size()) {
                    responseList[i - 1] = requestMixByOwners.get(currentKey.getStartIndex());
                } else {
                    responseList[i] = requestMixByOwners.get(currentKey.getStartIndex());
                }
                currentKey.setStartIndex(currentKey.getStartIndex() + 1);
                currentKey.setPostCount(currentKey.getPostCount() - 1);
            }
        }

        return postMapper.requestListToResponseList(responseList);
    }

    private GetPostsUserDto addOwner(Integer userId, Integer ownerId) {
        User owner = userRepository.findCustomById(ownerId);

        GetPostsUserDto getPostsUserDto = new GetPostsUserDto();
        getPostsUserDto.setId(owner.getId());
        getPostsUserDto.setUsername(owner.getUsername());
        getPostsUserDto.setFull_name(owner.getFull_name());
        getPostsUserDto.setProfile_picture(owner.getProfile_picture());
        Boolean controlRequestedUserFollow = controlRequestedUserFollow(userId, ownerId);
        getPostsUserDto.setFollowed(controlRequestedUserFollow);

        return getPostsUserDto;

    }

    private Boolean controlRequestedUserLike(Integer userId, Post post) {
        Like like = likeRepository.likeByUserIdAndPostId(userId, post.getId());
        return Objects.nonNull(like);

    }

    private Boolean controlRequestedUserFollow(Integer userId, Integer ownerId) {
        Follow follow = followRepository.followByUserIdAndOwnerId(userId, ownerId);
        return Objects.nonNull(follow);
    }


}









