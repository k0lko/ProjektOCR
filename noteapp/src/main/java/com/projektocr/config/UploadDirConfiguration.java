package com.projektocr.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.projektocr")
public class UploadDirConfiguration {
    @Bean
    public String uploadDir() {
        return "";
    }
}
