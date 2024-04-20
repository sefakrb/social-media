package scorp.socialmedia.user.service;

import scorp.socialmedia.user.model.entity.User;
import scorp.socialmedia.user.model.dto.RequestCreateUser;
import scorp.socialmedia.user.model.dto.ResponseCreateUser;

public interface IUserService {
    ResponseCreateUser createUser(RequestCreateUser requestCreateUser);

    User getUser(Integer id);
}
