package io.spring.oscon.fortunesui;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;

/**
 * @author Spencer Gibb
 */
@Service
public class FortunesService {
    @Autowired
    private RestOperations rest;

    @Autowired
    private FortunesuiApp.Fortunes fortunes;

    @HystrixCommand(fallbackMethod = "defaultFortune")
    public String fortune() {
        return fortunes.fortune();
    }

    public String defaultFortune() {
        return "This isn't your day";
    }
}
