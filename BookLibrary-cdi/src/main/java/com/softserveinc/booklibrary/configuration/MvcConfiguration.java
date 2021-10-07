package com.softserveinc.booklibrary.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan(basePackages="com.softserveinc.booklibrary.controller")
@EnableWebMvc
public class MvcConfiguration {}
