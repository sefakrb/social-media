package scorp.socialmedia.user.service;

import org.springframework.data.domain.Page;
import scorp.socialmedia.user.model.dto.CreateUserRequest;
import scorp.socialmedia.user.model.dto.CreateUserResponse;
import scorp.socialmedia.user.model.dto.UserResponse;

public interface IUserService {
    CreateUserResponse createUser(CreateUserRequest createUserRequest);
    UserResponse getUserById(Long id);
    Page<UserResponse> getAllUsers(int page, int size);
}
