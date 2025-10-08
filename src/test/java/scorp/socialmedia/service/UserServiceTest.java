package scorp.socialmedia.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import scorp.socialmedia.common.model.mapper.ICommonMapper;
import scorp.socialmedia.exception.ResourceNotFoundException;
import scorp.socialmedia.user.model.dto.CreateUserRequest;
import scorp.socialmedia.user.model.dto.CreateUserResponse;
import scorp.socialmedia.user.model.dto.UserResponse;
import scorp.socialmedia.user.model.entity.User;
import scorp.socialmedia.user.model.mapper.IUserMapper;
import scorp.socialmedia.user.model.repository.UserRepository;
import scorp.socialmedia.user.service.implementation.UserService;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit tests for UserService.
 * Tests the business logic for user management.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("User Service Tests")
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private IUserMapper userMapper;

    @Mock
    private ICommonMapper commonMapper;

    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("Should create user successfully")
    void shouldCreateUserSuccessfully() {
        // Given
        CreateUserRequest request = new CreateUserRequest(
                "john_doe",
                "john.doe@example.com",
                "John Doe",
                "https://example.com/profile.jpg",
                "Software developer"
        );

        User userToSave = User.builder()
                .username("john_doe")
                .email("john.doe@example.com")
                .fullName("John Doe")
                .profilePicture("https://example.com/profile.jpg")
                .bio("Software developer")
                .isActive(true)
                .build();

        User savedUser = User.builder()
                .id(1L)
                .username("john_doe")
                .email("john.doe@example.com")
                .fullName("John Doe")
                .profilePicture("https://example.com/profile.jpg")
                .bio("Software developer")
                .isActive(true)
                .build();

        CreateUserResponse expectedResponse = new CreateUserResponse(
                1L,
                "john_doe",
                "john.doe@example.com",
                "John Doe"
        );

        when(userRepository.findByUsername(request.username())).thenReturn(Optional.empty());
        when(userRepository.findByEmail(request.email())).thenReturn(Optional.empty());
        when(userMapper.requestCreateUserToUser(request)).thenReturn(userToSave);
        doNothing().when(commonMapper).setCreatedAt(any(User.class));
        when(userRepository.save(any(User.class))).thenReturn(savedUser);
        when(userMapper.userToResonseCreateUser(any(User.class))).thenReturn(expectedResponse);

        // When
        CreateUserResponse response = userService.createUser(request);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.id()).isEqualTo(1L);
        assertThat(response.username()).isEqualTo("john_doe");
        assertThat(response.email()).isEqualTo("john.doe@example.com");
        assertThat(response.fullName()).isEqualTo("John Doe");

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("Should get user by ID successfully")
    void shouldGetUserByIdSuccessfully() {
        // Given
        Long userId = 1L;
        User user = User.builder()
                .id(userId)
                .username("john_doe")
                .email("john.doe@example.com")
                .fullName("John Doe")
                .profilePicture("https://example.com/profile.jpg")
                .bio("Software developer")
                .isActive(true)
                .build();

        UserResponse expectedResponse = new UserResponse(
                userId,
                "john_doe",
                "john.doe@example.com",
                "John Doe",
                "https://example.com/profile.jpg",
                "Software developer",
                true,
                System.currentTimeMillis()
        );

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userMapper.userToUserResponse(user)).thenReturn(expectedResponse);

        // When
        UserResponse response = userService.getUserById(userId);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.id()).isEqualTo(userId);
        assertThat(response.username()).isEqualTo("john_doe");
        assertThat(response.email()).isEqualTo("john.doe@example.com");
        assertThat(response.fullName()).isEqualTo("John Doe");
        assertThat(response.isActive()).isTrue();

        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    @DisplayName("Should throw exception when user not found")
    void shouldThrowExceptionWhenUserNotFound() {
        // Given
        Long userId = 999L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> userService.getUserById(userId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("User not found with ID: " + userId);

        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    @DisplayName("Should get all users with pagination")
    void shouldGetAllUsersWithPagination() {
        // Given
        Pageable pageable = PageRequest.of(0, 10);
        User user1 = User.builder()
                .id(1L)
                .username("john_doe")
                .email("john.doe@example.com")
                .fullName("John Doe")
                .isActive(true)
                .build();

        User user2 = User.builder()
                .id(2L)
                .username("jane_doe")
                .email("jane.doe@example.com")
                .fullName("Jane Doe")
                .isActive(true)
                .build();

        List<User> users = List.of(user1, user2);

        UserResponse response1 = new UserResponse(
                1L,
                "john_doe",
                "john.doe@example.com",
                "John Doe",
                null,
                null,
                true,
                System.currentTimeMillis()
        );

        UserResponse response2 = new UserResponse(
                2L,
                "jane_doe",
                "jane.doe@example.com",
                "Jane Doe",
                null,
                null,
                true,
                System.currentTimeMillis()
        );

        Page<User> userPage = new PageImpl<>(users, pageable, 2);
        when(userRepository.findAll(pageable)).thenReturn(userPage);
        when(userMapper.userToUserResponse(user1)).thenReturn(response1);
        when(userMapper.userToUserResponse(user2)).thenReturn(response2);

        // When
        Page<UserResponse> response = userService.getAllUsers(0, 10);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getContent()).hasSize(2);
        assertThat(response.getTotalElements()).isEqualTo(2);
        assertThat(response.getContent().get(0).username()).isEqualTo("john_doe");
        assertThat(response.getContent().get(1).username()).isEqualTo("jane_doe");

        verify(userRepository, times(1)).findAll(pageable);
    }
}

