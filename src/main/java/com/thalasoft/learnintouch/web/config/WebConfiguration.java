package com.thalasoft.learnintouch.web.config;

import java.util.List;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.util.StringUtils;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

@Configuration
@EnableWebMvc
@EnableSpringDataWebSupport
@ComponentScan(basePackages = { "com.thalasoft.learnintouch.web.rest.controller" })
public class WebConfiguration extends WebMvcConfigurerAdapter {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
    	PageableHandlerMethodArgumentResolver resolver = new PageableHandlerMethodArgumentResolver();
        resolver.setFallbackPageable(new PageRequest(0, 10));
        argumentResolvers.add(resolver);
        super.addArgumentResolvers(argumentResolvers);
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames("classpath:messages/messages", "classpath:messages/validation");
        // If true, the key of the message will be displayed if the key is not found, instead of throwing an exception
        messageSource.setUseCodeAsDefaultMessage(true);
        messageSource.setDefaultEncoding("UTF-8");
        // The value 0 means always reload the messages to be developer friendly
        messageSource.setCacheSeconds(0);
        return messageSource;
    }

    // The locale interceptor provides a way to switch the language in any page just by passing the lang=’en’, lang=’fr’, and so on to the url
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("lang");
        registry.addInterceptor(localeChangeInterceptor);
    }
    
    // The locale resolver allows a cookie to memorize the user chosen locale
    @Bean
    public LocaleResolver localeResolver() {
    	CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();
    	cookieLocaleResolver.setDefaultLocale(StringUtils.parseLocaleString("en"));
    	return cookieLocaleResolver;
    }
    
	@Bean
	public UrlBasedViewResolver urlBasedViewResolver() {
		UrlBasedViewResolver urlBasedViewResolver = new InternalResourceViewResolver();
		urlBasedViewResolver.setViewClass(JstlView.class);
		urlBasedViewResolver.setPrefix("/WEB-INF/jsp/");
		urlBasedViewResolver.setSuffix(".jsp");
		return urlBasedViewResolver;
	}

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }
    
//  @Override
//  public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
//      configurer.enable();
//  }
  
//    @Override
//    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
//        configurer.useJaf(false).
//        ignoreAcceptHeader(true).
//        favorParameter(false).
//        favorPathExtension(true).
//        defaultContentType(MediaType.APPLICATION_JSON).
//        mediaType("html", MediaType.TEXT_HTML).
//        mediaType("xml", MediaType.APPLICATION_XML).
//        mediaType("json", MediaType.APPLICATION_JSON);
//    }

//    @Bean
//    public SimpleMappingExceptionResolver simpleMappingExceptionResolver() {
//        SimpleMappingExceptionResolver simpleMappingExceptionResolver = new SimpleMappingExceptionResolver();
//        Properties mappings = new Properties();
//        mappings.put("org.springframework.web.servlet.PageNotFound", "p404");
//        mappings.put("org.springframework.dao.DataAccessException", "dataAccessFailure");
//        mappings.put("org.springframework.transaction.TransactionException", "dataAccessFailure");
//        simpleMappingExceptionResolver.setExceptionMappings(mappings);
//        return simpleMappingExceptionResolver;
//    }
    
//    @Override
//    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//        // equivalent to <mvc:message-converters>
//    }

//    @Bean
//    public ServletWebArgumentResolverAdapter resolver() {
//        return new ServletWebArgumentResolverAdapter(pageable());
//    }

//    @Bean
//    public PageableArgumentResolver pageable() {
//        return new PageableArgumentResolver();
//    }

//    @Override
//    public void addViewControllers(ViewControllerRegistry aRegistry) {
//        aRegistry.addViewController("/").setViewName("index");
//    }

}
