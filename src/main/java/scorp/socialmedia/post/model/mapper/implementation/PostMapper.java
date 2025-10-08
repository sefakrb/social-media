package scorp.socialmedia.post.model.mapper.implementation;

import org.springframework.stereotype.Component;
import scorp.socialmedia.post.model.dto.CreatePostRequest;
import scorp.socialmedia.post.model.dto.CreatePostResponse;
import scorp.socialmedia.post.model.dto.PostResponse;
import scorp.socialmedia.post.model.entity.Post;
import scorp.socialmedia.post.model.mapper.IPostMapper;

@Component
public class PostMapper implements IPostMapper {
    @Override
    public Post requestCreatePostToPost(CreatePostRequest createPostRequest) {
        Post post = new Post();
        post.setDescription(createPostRequest.description());
        post.setImageUrl(createPostRequest.imageUrl());
        post.setUserId(createPostRequest.userId());
        return post;
    }

    @Override
    public CreatePostResponse postToResponseCreatePost(Post newPost) {
        return new CreatePostResponse(
                newPost.getId(),
                newPost.getUserId(),
                newPost.getDescription(),
                newPost.getImageUrl(),
                newPost.getCreatedAt()
        );
    }

    @Override
    public PostResponse postToPostResponse(Post post) {
        return new PostResponse(
                post.getId(),
                post.getDescription(),
                null,
                post.getImageUrl(),
                post.getCreatedAt(),
                false
        );
    }
}
