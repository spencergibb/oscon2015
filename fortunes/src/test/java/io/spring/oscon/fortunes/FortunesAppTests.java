package io.spring.oscon.fortunes;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = FortunesApp.class)
@WebAppConfiguration
public class FortunesAppTests {

	@Test
	public void contextLoads() {
	}

}
