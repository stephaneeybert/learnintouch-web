package com.thalasoft.learnintouch.web.rest.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.thalasoft.learnintouch.web.config.WebSecurityConfiguration;
import com.thalasoft.learnintouch.web.config.WebSecurityInitializer;
import com.thalasoft.learnintouch.web.security.RestAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
@ComponentScan(basePackages = { "com.thalasoft.learnintouch.web.security" })
//@ImportResource({ "classpath:web-security.xml" })
@Import({ WebSecurityInitializer.class })
@Order(1)
public class WebSecurityTestConfiguration extends WebSecurityConfigurerAdapter {

	private static Logger logger = LoggerFactory.getLogger(WebSecurityConfiguration.class);

	@Autowired
	RestAuthenticationEntryPoint restAuthenticationEntryPoint;

	@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("stephane").password("mypassword").roles("ADMIN");
    }

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.csrf().disable()
		.httpBasic()
		.authenticationEntryPoint(restAuthenticationEntryPoint)
		.and()
		.authorizeRequests()
		.antMatchers("/**").hasRole("ADMIN")
		.anyRequest().authenticated();
	}
	
}
