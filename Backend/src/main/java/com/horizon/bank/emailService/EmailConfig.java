package com.horizon.bank.emailService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class EmailConfig {
  private final Environment environment;

  public EmailConfig(Environment environment) {
    this.environment = environment;
  }

  @Bean
  public JavaMailSenderImpl javaMailSender() {

    JavaMailSenderImpl sender = new JavaMailSenderImpl();

    sender.setHost("smtp.gmail.com");
    sender.setPort(587);

    sender.setUsername(environment.getProperty("spring.mail.username"));
    sender.setPassword(environment.getProperty("spring.mail.password"));

    Properties props = sender.getJavaMailProperties();

    props.put("mail.transport.protocol", "smtp");
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.debug", "false");

    return sender;
  }
}
