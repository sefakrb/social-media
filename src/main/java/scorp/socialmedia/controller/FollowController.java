package scorp.socialmedia.controller;

import org.springframework.web.bind.annotation.*;
import scorp.socialmedia.follow.model.dto.RequestFollow;
import scorp.socialmedia.follow.model.dto.ResponseFollow;
import scorp.socialmedia.follow.model.entity.Follow;
import scorp.socialmedia.follow.service.IFollowService;

import java.util.List;

@RestController
@RequestMapping("/follow")
public class FollowController {
    private final IFollowService followService;

    public FollowController(IFollowService followService) {
        this.followService = followService;
    }

    @PostMapping
    public ResponseFollow follow(@RequestBody RequestFollow requestFollow) {
        return followService.follow(requestFollow);
    }

    @GetMapping
    public List<Follow> getFollows(){
        return followService.getFollows();
    }
}
