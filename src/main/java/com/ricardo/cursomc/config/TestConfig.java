package com.ricardo.cursomc.config;

import com.ricardo.cursomc.services.DBService;
import com.ricardo.cursomc.services.EmailService;
import com.ricardo.cursomc.services.MockEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.text.ParseException;

@Configuration
@Profile("test")
public class TestConfig {

    @Autowired
    private DBService dbService;

    @Bean
    public boolean instantiateDatabase() throws ParseException {
        dbService.instantiateTestDataBGase();
        return true;
    }

    @Bean
    public EmailService emailService(){
        return new MockEmailService();
    }
}
