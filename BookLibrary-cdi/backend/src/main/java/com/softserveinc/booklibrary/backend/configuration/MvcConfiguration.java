package com.softserveinc.booklibrary.backend.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan(basePackages = "com.softserveinc.booklibrary.backend")
@EnableWebMvc
public class MvcConfiguration {
}
