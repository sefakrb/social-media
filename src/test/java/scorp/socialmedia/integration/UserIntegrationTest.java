package scorp.socialmedia.integration;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import scorp.socialmedia.user.model.dto.CreateUserRequest;
import scorp.socialmedia.user.model.entity.User;
import scorp.socialmedia.user.model.repository.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for User functionality.
 * Tests the complete flow from controller to database.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@DisplayName("User Integration Tests")
class UserIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Should create and retrieve user successfully")
    void shouldCreateAndRetrieveUserSuccessfully() throws Exception {
        // Given
        CreateUserRequest request = new CreateUserRequest(
                "integration_test_user",
                "integration@example.com",
                "Integration Test User",
                "https://example.com/profile.jpg",
                "Integration test user"
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CreateUserRequest> entity = new HttpEntity<>(request, headers);

        // When - Create user
        ResponseEntity<String> createResponse = restTemplate.postForEntity(
                "http://localhost:" + port + "/api/v1/users", 
                entity, 
                String.class
        );

        // Then - Verify user was created
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(createResponse.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON);

        // Verify user was saved in database
        User savedUser = userRepository.findByUsername("integration_test_user").orElse(null);
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getEmail()).isEqualTo("integration@example.com");
        assertThat(savedUser.getFullName()).isEqualTo("Integration Test User");

        // When - Retrieve user
        ResponseEntity<String> getResponse = restTemplate.getForEntity(
                "http://localhost:" + port + "/api/v1/users/" + savedUser.getId(), 
                String.class
        );

        // Then - Verify user was retrieved
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON);
    }

    @Test
    @DisplayName("Should return validation error for duplicate username")
    void shouldReturnValidationErrorForDuplicateUsername() throws Exception {
        // Given - Create first user
        User existingUser = User.builder()
                .username("duplicate_user")
                .email("first@example.com")
                .fullName("First User")
                .isActive(true)
                .build();
        userRepository.saveAndFlush(existingUser);

        // When - Try to create user with same username
        CreateUserRequest request = new CreateUserRequest(
                "duplicate_user", // Same username
                "second@example.com",
                "Second User",
                null,
                null
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CreateUserRequest> entity = new HttpEntity<>(request, headers);

        // Then - Should return validation error
        ResponseEntity<String> response = restTemplate.postForEntity(
                "http://localhost:" + port + "/api/v1/users", 
                entity, 
                String.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON);
    }

    @Test
    @DisplayName("Should return 404 for non-existent user")
    void shouldReturn404ForNonExistentUser() throws Exception {
        // When & Then
        ResponseEntity<String> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/api/v1/users/999", 
                String.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON);
    }
}
