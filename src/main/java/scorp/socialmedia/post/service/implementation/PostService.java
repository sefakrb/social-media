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
        List<Post> existingPostsList = postRepository.postsById(requestGetPosts.getPost_ids());
        List<Like> requestedUserLikes = likeRepository.likesByUserIdAndPostIds(requestGetPosts.getUser_id(), requestGetPosts.getPost_ids());
        HashMap<Integer, Post> existingPostsMap = new HashMap<>();
        List<Integer> postOwnerIds = new ArrayList<>();

        for (Post post : existingPostsList) {
            existingPostsMap.putIfAbsent(post.getId(), post);
            postOwnerIds.add(post.getUser_id());
        }

        List<User> postOwnersList = userRepository.findUsersByPostIds(postOwnerIds);
        List<Follow> requestedUserFollowList = followRepository.findFollowsByUserIdAndOwnerIds(requestGetPosts.getUser_id(), postOwnerIds);

        for (int i = 0; i < requestGetPosts.getPost_ids().size(); i++) {
            if (Objects.isNull(existingPostsMap.get(requestGetPosts.getPost_ids().get(i)))) {
                responseGetPosts.add(null);
            } else {
                Post post = existingPostsMap.get(requestGetPosts.getPost_ids().get(i));
                ResponseGetPosts getPosts = new ResponseGetPosts();
                getPosts.setId(post.getId());
                getPosts.setDescription(post.getDescription());
                getPosts.setImage(post.getImage());
                getPosts.setCreated_at(post.getCreated_at());
                Boolean controlRequestedUserLike = controlRequestedUserLike(requestedUserLikes, post.getId());
                getPosts.setLiked(controlRequestedUserLike);
                GetPostsUserDto getPostsUserDto = addOwner(postOwnersList, requestedUserFollowList, post);
                getPosts.setOwner(getPostsUserDto);

                responseGetPosts.add(getPosts);
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
        int loopTimes = requestMixByOwners.size();
        int lastAddedIndex = 0;

        for (int i = 0; i < loopTimes; i++) {
            CountDto currentKey = counter.get(uniqueKeys[i % uniqueKeysLength]);
            if (currentKey.getPostCount() > 0) {
                responseList[lastAddedIndex] = requestMixByOwners.get(currentKey.getStartIndex());
                lastAddedIndex = lastAddedIndex + 1;
                currentKey.setStartIndex(currentKey.getStartIndex() + 1);
                currentKey.setPostCount(currentKey.getPostCount() - 1);
            } else {
                loopTimes = loopTimes + 1;
            }
        }
        return postMapper.requestListToResponseList(responseList);
    }

    private GetPostsUserDto addOwner(List<User> postOwnersList, List<Follow> requestedUserFollowList, Post post) {
        GetPostsUserDto getPostsUserDto = new GetPostsUserDto();
        boolean isFollow = false;

        for (Follow follow : requestedUserFollowList) {
            if (post.getUser_id().equals(follow.getFollowing_id())) {
                isFollow = true;
                break;
            }
        }

        for (User user : postOwnersList) {
            if (post.getUser_id().equals(user.getId())) {
                getPostsUserDto.setId(user.getId());
                getPostsUserDto.setUsername(user.getUsername());
                getPostsUserDto.setFull_name(user.getFull_name());
                getPostsUserDto.setProfile_picture(user.getProfile_picture());
                getPostsUserDto.setFollowed(isFollow);
            }
        }
        return getPostsUserDto;
    }

    private Boolean controlRequestedUserLike(List<Like> requestedUserLikes, Integer postId) {
        for (Like like : requestedUserLikes) {
            if (Objects.equals(like.getPost_id(), postId)) {
                return true;
            }
        }
        return false;
    }

}









