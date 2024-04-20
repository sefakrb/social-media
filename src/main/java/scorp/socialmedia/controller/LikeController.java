package scorp.socialmedia.controller;

import org.springframework.web.bind.annotation.*;
import scorp.socialmedia.like.dto.RequestLikePost;
import scorp.socialmedia.like.dto.ResponseLikePost;
import scorp.socialmedia.like.model.entity.Like;
import scorp.socialmedia.like.service.ILikeService;

import java.util.List;

@RestController
@RequestMapping("/like")
public class LikeController {
    private final ILikeService likeService;

    public LikeController(ILikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping
    public ResponseLikePost likePost(@RequestBody RequestLikePost requestLikePost) {
        return likeService.likePost(requestLikePost);
    }

    @GetMapping
    public List<Like> getLikedPosts(){
        return likeService.geLikedPosts();
    }
}
