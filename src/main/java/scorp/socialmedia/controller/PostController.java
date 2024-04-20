package scorp.socialmedia.controller;

import org.springframework.web.bind.annotation.*;
import scorp.socialmedia.post.model.dto.*;
import scorp.socialmedia.post.model.entity.Post;
import scorp.socialmedia.post.service.IPostService;

import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {
    private final IPostService postService;
    public PostController(IPostService postService) {
        this.postService = postService;
    }
    @PostMapping
    public ResponseCreatePost createPost(@RequestBody RequestCreatePost requestCreatePost) {
        return postService.createPost(requestCreatePost);
    }

    @GetMapping
    public List<Post> getPost() {
        return postService.getPosts();
    }

    @GetMapping(value = "/get-posts")
    public List<ResponseGetPosts> get_posts(@RequestBody RequestGetPosts requestGetPosts){
        return postService.get_posts(requestGetPosts);
    }

    @PostMapping(value = "/mix-by-owners")
    public List<ResponseMixByOwners> mix_by_owners(@RequestBody List<RequestMixByOwners> requestMixByOwners){
        return postService.mix_by_owners(requestMixByOwners);
    }
}
