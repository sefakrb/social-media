package scorp.socialmedia;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import scorp.socialmedia.controller.HealthController;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SocialmediaApplicationTests {

	@Autowired
	private HealthController healthController;

	@Test
	void contextLoads() {
	}

	@Test
	void testHealthController(){
		// Test that the health controller is properly wired
		assertThat(healthController).isNotNull();
	}

}
