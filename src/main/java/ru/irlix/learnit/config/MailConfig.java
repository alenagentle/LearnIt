package ru.irlix.learnit.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties("mail")
public class MailConfig {

    private String host;

    private String port;

    private String senderAddress;

    private String senderPassword;
}
