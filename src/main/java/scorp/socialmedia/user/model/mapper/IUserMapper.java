package scorp.socialmedia.user.model.mapper;

import scorp.socialmedia.user.model.entity.User;
import scorp.socialmedia.user.model.dto.RequestCreateUser;
import scorp.socialmedia.user.model.dto.ResponseCreateUser;

public interface IUserMapper {
    User requestCreateUserToUser(RequestCreateUser requestCreateUser);
    ResponseCreateUser userToResonseCreateUser(User newUser);
}
