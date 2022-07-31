package com.thalasoft.learnintouch.web.config;

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

import com.thalasoft.learnintouch.web.security.CustomAuthenticationProvider;
import com.thalasoft.learnintouch.web.security.RestAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
@ComponentScan(basePackages = { "com.thalasoft.learnintouch.web.security" })
//@ImportResource({ "classpath:web-security.xml" })
@Import({ WebSecurityInitializer.class })
@Order(2)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	private static Logger logger = LoggerFactory.getLogger(WebSecurityConfiguration.class);

    @Autowired
	CustomAuthenticationProvider customAuthenticationProvider;

	@Autowired
	RestAuthenticationEntryPoint restAuthenticationEntryPoint;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(customAuthenticationProvider);
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

//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web.ignoring().antMatchers("/resources/**");
//    }

//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
////		http.authorizeRequests().antMatchers("/**").authenticated().and().httpBasic().authenticationEntryPoint("myBasicAuthenticationEntryPoint");
//		
//		http.authorizeRequests().antMatchers("/**").hasRole("ADMIN").and().httpBasic();
//		
//        http.authorizeRequests().antMatchers("/admin/login", "/admin/logout", "/admin/denied").permitAll()
//        .antMatchers("/admin/**").hasRole("ADMIN")
//        .and()
//        .formLogin()
//        .loginPage("/admin/login")
//        .defaultSuccessUrl("/admin/list")
//        .failureUrl("/admin/denied?failed=true")
//        .and()
//        .rememberMe();
//
//        http.logout().logoutUrl("/admin/logout").logoutSuccessUrl("/admin/login").deleteCookies("JSESSIONID");
//
////        http.rememberMe().rememberMeServices(rememberMeServices()).key("password");        
//	}

//    httpSecurity
//    .authorizeRequests()
//        .antMatchers("/login","/login.request","/logout").permitAll()
//        .anyRequest().hasRole("RoleEmployee")
//.and()
//    .formLogin()
//        .loginPage("/login.request")
//        .loginProcessingUrl("/login")
//        .failureUrl("/login.request?error")
//        .permitAll()
//.and()
//    .logout()
//        .logoutUrl("/logout")
//        .permitAll()
//        .logoutSuccessUrl("/login.request")
//;