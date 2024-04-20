package scorp.socialmedia.controller;

import org.springframework.web.bind.annotation.*;
import scorp.socialmedia.user.model.entity.User;
import scorp.socialmedia.user.model.dto.RequestCreateUser;
import scorp.socialmedia.user.model.dto.ResponseCreateUser;
import scorp.socialmedia.user.service.IUserService;

@RestController
@RequestMapping("/user")
public class UserController {
    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseCreateUser createUser(@RequestBody RequestCreateUser requestCreateUser){
        return userService.createUser(requestCreateUser);
    }

    @GetMapping
    public User getUser(@RequestParam(name = "id") Integer id) {
        return userService.getUser(id);
    }
}
