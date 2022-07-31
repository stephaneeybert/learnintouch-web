package com.thalasoft.learnintouch.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.thalasoft.learnintouch.data.config.DatabaseConfiguration;

@Configuration
@Import({ DatabaseConfiguration.class })
public class ApplicationConfiguration extends WebMvcConfigurerAdapter {

    // Declare "application" scope beans here, that is, beans that are not only used by the web context
    
}
