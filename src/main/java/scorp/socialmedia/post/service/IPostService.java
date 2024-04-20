package scorp.socialmedia.post.service;

import scorp.socialmedia.post.model.dto.*;
import scorp.socialmedia.post.model.entity.Post;

import java.util.List;

public interface IPostService {
    ResponseCreatePost createPost(RequestCreatePost requestCreatePost);

    List<Post> getPosts();

    List<ResponseGetPosts> get_posts(RequestGetPosts requestGetPosts);

    List<ResponseMixByOwners> mix_by_owners(List<RequestMixByOwners> requestMixByOwners);
}
