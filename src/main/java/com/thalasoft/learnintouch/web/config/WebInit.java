package com.thalasoft.learnintouch.web.config;

import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class WebInit implements WebApplicationInitializer {

	private static Logger logger = LoggerFactory.getLogger(WebInit.class);

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		registerListener(servletContext);
	    
        registerDispatcherServlet(servletContext);
        
        registerJspServlet(servletContext);
	}
	
	private void registerListener(ServletContext servletContext) {
        // Create the root application context
        AnnotationConfigWebApplicationContext appContext = createContext(ApplicationConfiguration.class, WebSecurityConfiguration.class);
        
        // Set the application display name
        appContext.setDisplayName("LearnInTouch");

        // Create the Spring Container shared by all servlets and filters
        servletContext.addListener(new ContextLoaderListener(appContext));
	}
	
    private void registerDispatcherServlet(ServletContext servletContext) {
        AnnotationConfigWebApplicationContext webApplicationContext = createContext(WebConfiguration.class);

        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", new DispatcherServlet(webApplicationContext));
        dispatcher.setLoadOnStartup(1);
        
        Set<String> mappingConflicts = dispatcher.addMapping("/");

        if (!mappingConflicts.isEmpty()) {
          for (String mappingConflict : mappingConflicts) {
            logger.error("Mapping conflict: " + mappingConflict);
          }
          throw new IllegalStateException(
              "The servlet cannot be mapped to '/'");
        }
    }
    
    private void registerJspServlet(ServletContext servletContext) {
	}

    private AnnotationConfigWebApplicationContext createContext(final Class... modules) {
        AnnotationConfigWebApplicationContext appContext = new AnnotationConfigWebApplicationContext();
        appContext.register(modules);
        return appContext;
    }
 
}
