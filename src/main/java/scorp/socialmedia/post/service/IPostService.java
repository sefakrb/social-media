package scorp.socialmedia.post.service;

import org.springframework.data.domain.Page;
import scorp.socialmedia.post.model.dto.*;

import java.util.List;

public interface IPostService {
    CreatePostResponse createPost(CreatePostRequest createPostRequest);
    Page<PostResponse> getAllPosts(int page, int size);
    Page<PostResponse> getPostsByUser(Long userId, int page, int size);
    List<PostWithContextResponse> getPostsWithContext(GetPostsWithContextRequest request);
    List<MixedPostResponse> mixPostsByOwners(List<MixPostRequest> request);
}
