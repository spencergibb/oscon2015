package io.spring.oscon.fortunesui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestOperations;

@SpringCloudApplication
@EnableConfigurationProperties
@EnableFeignClients
@RestController
public class FortunesuiApp {

	@Autowired
	private LoadBalancerClient lb;

	@Autowired
	private FortunesService fortunesService;

	@Autowired
	private FortunesuiProperties properties;

	@RequestMapping("/choose")
	public Object choose() {
		return lb.choose("fortunes");
	}

	@RequestMapping(value = "/", produces = "text/html")
	public String fortune() {
		// String fortune = rest.getForObject("http://fortunes/", String.class);
		String fortune = fortunesService.fortune();
		return String.format("<html><head><title>Fortunes</title></head><body>%s%s</body>",
				properties.getPrefix(), fortune);
	}

	@FeignClient("fortunes")
	public interface Fortunes {
		@RequestMapping(value = "/", method = RequestMethod.GET)
		String fortune();
	}

	@Bean
	public FortunesuiProperties fortunesuiProperties() {
		return new FortunesuiProperties();
	}

	public static void main(String[] args) {
		SpringApplication.run(FortunesuiApp.class, args);
	}
}
