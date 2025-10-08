package scorp.socialmedia.user.model.mapper;

import scorp.socialmedia.user.model.entity.User;
import scorp.socialmedia.user.model.dto.CreateUserRequest;
import scorp.socialmedia.user.model.dto.CreateUserResponse;
import scorp.socialmedia.user.model.dto.UserResponse;

public interface IUserMapper {
    User requestCreateUserToUser(CreateUserRequest createUserRequest);
    CreateUserResponse userToResonseCreateUser(User newUser);
    UserResponse userToUserResponse(User user);
}
