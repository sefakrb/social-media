package scorp.socialmedia.post.model.mapper;

import scorp.socialmedia.post.model.dto.RequestCreatePost;
import scorp.socialmedia.post.model.dto.RequestMixByOwners;
import scorp.socialmedia.post.model.dto.ResponseCreatePost;
import scorp.socialmedia.post.model.dto.ResponseMixByOwners;
import scorp.socialmedia.post.model.entity.Post;

import java.util.List;

public interface IPostMapper {
    Post requestCreatePostToPost(RequestCreatePost requestCreatePost);

    ResponseCreatePost postToResponseCreatePost(Post newPost);

    List<ResponseMixByOwners> requestListToResponseList(RequestMixByOwners[] responseList);
}
