package scorp.socialmedia.user.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import scorp.socialmedia.common.model.mapper.ICommonMapper;
import scorp.socialmedia.user.model.entity.User;
import scorp.socialmedia.user.model.mapper.IUserMapper;
import scorp.socialmedia.user.model.repository.UserRepository;
import scorp.socialmedia.user.model.dto.RequestCreateUser;
import scorp.socialmedia.user.model.dto.ResponseCreateUser;
import scorp.socialmedia.user.service.IUserService;

@Service
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final IUserMapper userMapper;
    private final ICommonMapper commonMapper;

    @Autowired
    public UserService(UserRepository userRepository, IUserMapper userMapper, ICommonMapper commonMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.commonMapper = commonMapper;

    }
    @Override
    public ResponseCreateUser createUser(RequestCreateUser requestCreateUser) {
        User newUser = userMapper.requestCreateUserToUser(requestCreateUser);
        commonMapper.setCreatedAt(newUser);
        userRepository.save(newUser);
        return userMapper.userToResonseCreateUser(newUser);
    }

    @Override
    public User getUser(Integer id) {
        return userRepository.findById(id).orElse(null);
    }
}
