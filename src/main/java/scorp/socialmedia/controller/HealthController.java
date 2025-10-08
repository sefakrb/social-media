package scorp.socialmedia.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Health check and utility controller.
 * Provides basic health check and system information endpoints.
 */
@RestController
@RequestMapping("/health")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Health Check", description = "APIs for health checks and system information")
public class HealthController {

    /**
     * Basic health check endpoint.
     *
     * @return health status information
     */
    @GetMapping
    @Operation(summary = "Health check", description = "Returns basic health status of the application")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Application is healthy")
    })
    public ResponseEntity<Map<String, Object>> healthCheck() {
        log.debug("Health check requested");
        
        Map<String, Object> healthStatus = Map.of(
                "status", "UP",
                "timestamp", LocalDateTime.now(),
                "service", "Social Media API",
                "version", "1.0.0"
        );
        
        return ResponseEntity.ok(healthStatus);
    }

    /**
     * Simple calculation endpoint for testing purposes.
     *
     * @return calculation result
     */
    @GetMapping("/test")
    @Operation(summary = "Test calculation", description = "Simple calculation endpoint for testing API connectivity")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Calculation completed successfully")
    })
    public ResponseEntity<Map<String, Object>> testCalculation() {
        log.debug("Test calculation requested");
        
        int result = 2 + 2;
        
        Map<String, Object> response = Map.of(
                "calculation", "2 + 2",
                "result", result,
                "timestamp", LocalDateTime.now()
        );
        
        return ResponseEntity.ok(response);
    }
}
