package io.spring.oscon.fortunesui;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Spencer Gibb
 */
@ConfigurationProperties("fortunesui")
public class FortunesuiProperties {
    private String prefix = "You're fortune: ";

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
