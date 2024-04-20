package scorp.socialmedia.user.model.mapper.implementation;

import org.springframework.stereotype.Component;
import scorp.socialmedia.user.model.entity.User;
import scorp.socialmedia.user.model.mapper.IUserMapper;
import scorp.socialmedia.user.model.dto.RequestCreateUser;
import scorp.socialmedia.user.model.dto.ResponseCreateUser;

@Component
public class UserMapper implements IUserMapper {
    @Override
    public User requestCreateUserToUser(RequestCreateUser requestCreateUser) {
        User user = new User();
        user.setUsername(requestCreateUser.getUsername());
        user.setEmail(requestCreateUser.getEmail());
        user.setFull_name(requestCreateUser.getFull_name());
        user.setProfile_picture(requestCreateUser.getProfile_picture());
        user.setBio(requestCreateUser.getBio());

        return user;
    }

    @Override
    public ResponseCreateUser userToResonseCreateUser(User newUser) {
        ResponseCreateUser responseCreateUser = new ResponseCreateUser();
        responseCreateUser.setUserName(newUser.getUsername());
        return responseCreateUser;
    }
}
