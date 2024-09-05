package scorp.socialmedia;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import scorp.socialmedia.controller.HelloWorldController;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SocialmediaApplicationTests {

	@Autowired
	private HelloWorldController helloWorldController;

	@Test
	void contextLoads() {
	}

	@Test
	void testHelloWorldController(){
		Integer sumResult = helloWorldController.sum();
		Integer result = 4;

		assertThat(sumResult).isEqualTo(result);
	}

}
