package scorp.socialmedia.post.model.mapper;

import scorp.socialmedia.post.model.dto.CreatePostRequest;
import scorp.socialmedia.post.model.dto.CreatePostResponse;
import scorp.socialmedia.post.model.dto.PostResponse;
import scorp.socialmedia.post.model.entity.Post;

public interface IPostMapper {
    Post requestCreatePostToPost(CreatePostRequest createPostRequest);

    CreatePostResponse postToResponseCreatePost(Post newPost);

    PostResponse postToPostResponse(Post post);
}
