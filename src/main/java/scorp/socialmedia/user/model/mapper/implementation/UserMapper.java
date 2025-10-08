package scorp.socialmedia.user.model.mapper.implementation;

import org.springframework.stereotype.Component;
import scorp.socialmedia.user.model.entity.User;
import scorp.socialmedia.user.model.mapper.IUserMapper;
import scorp.socialmedia.user.model.dto.CreateUserRequest;
import scorp.socialmedia.user.model.dto.CreateUserResponse;
import scorp.socialmedia.user.model.dto.UserResponse;

@Component
public class UserMapper implements IUserMapper {
    @Override
    public User requestCreateUserToUser(CreateUserRequest createUserRequest) {
        return User.builder()
                .username(createUserRequest.username())
                .email(createUserRequest.email())
                .fullName(createUserRequest.fullName())
                .profilePicture(createUserRequest.profilePicture())
                .bio(createUserRequest.bio())
                .build();
    }

    @Override
    public CreateUserResponse userToResonseCreateUser(User newUser) {
        return new CreateUserResponse(
                newUser.getId(),
                newUser.getUsername(),
                newUser.getEmail(),
                newUser.getFullName()
        );
    }

    @Override
    public UserResponse userToUserResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getFullName(),
                user.getProfilePicture(),
                user.getBio(),
                user.getIsActive(),
                user.getCreatedAt()
        );
    }
}
