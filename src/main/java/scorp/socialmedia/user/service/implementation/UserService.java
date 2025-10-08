package scorp.socialmedia.user.service.implementation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import scorp.socialmedia.common.model.mapper.ICommonMapper;
import scorp.socialmedia.user.model.entity.User;
import scorp.socialmedia.user.model.mapper.IUserMapper;
import scorp.socialmedia.user.model.repository.UserRepository;
import scorp.socialmedia.user.model.dto.CreateUserRequest;
import scorp.socialmedia.user.model.dto.CreateUserResponse;
import scorp.socialmedia.user.model.dto.UserResponse;
import scorp.socialmedia.user.service.IUserService;

@Service
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final IUserMapper userMapper;
    private final ICommonMapper commonMapper;

    public UserService(UserRepository userRepository, IUserMapper userMapper, ICommonMapper commonMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.commonMapper = commonMapper;
    }
    @Override
    public CreateUserResponse createUser(CreateUserRequest createUserRequest) {
        // Check if username already exists
        if (userRepository.findByUsername(createUserRequest.username()).isPresent()) {
            throw new scorp.socialmedia.exception.BusinessException("Username already exists: " + createUserRequest.username());
        }
        
        // Check if email already exists
        if (userRepository.findByEmail(createUserRequest.email()).isPresent()) {
            throw new scorp.socialmedia.exception.BusinessException("Email already exists: " + createUserRequest.email());
        }
        
        User newUser = userMapper.requestCreateUserToUser(createUserRequest);
        commonMapper.setCreatedAt(newUser);
        userRepository.save(newUser);
        return userMapper.userToResonseCreateUser(newUser);
    }

    @Override
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new scorp.socialmedia.exception.ResourceNotFoundException("User not found with ID: " + id));
        return userMapper.userToUserResponse(user);
    }

    @Override
    public Page<UserResponse> getAllUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> userPage = userRepository.findAll(pageable);
        return userPage.map(userMapper::userToUserResponse);
    }
}
