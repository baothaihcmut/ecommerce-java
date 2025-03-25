package com.ecommerceapp.libs.mailer;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@ConfigurationPropertiesScan
@ConfigurationProperties(prefix = "spring.mail")
public class MailProperties {
    private String host;
    private int port;
    private String username;
    private String password;
    private MailPropertiesConfig mail;

    @Data
    public static class MailPropertiesConfig {
        private SmtpProperties smtp;

        @Data
        public static class SmtpProperties {
            private boolean auth;
            private StartTlsProperties starttls;

            @Getter
            @Setter
            public static class StartTlsProperties {
                private boolean enable;
            }
        }
    }
}
