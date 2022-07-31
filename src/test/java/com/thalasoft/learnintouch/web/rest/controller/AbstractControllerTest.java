package com.thalasoft.learnintouch.web.rest.controller;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.thalasoft.learnintouch.web.config.ApplicationConfiguration;
import com.thalasoft.learnintouch.web.config.WebConfiguration;
import com.thalasoft.learnintouch.web.rest.config.WebSecurityTestConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration( classes = { ApplicationConfiguration.class, WebSecurityTestConfiguration.class, WebConfiguration.class })
@Transactional
public abstract class AbstractControllerTest {

	@Autowired
    private WebApplicationContext webApplicationContext;

	@Autowired
    private FilterChainProxy springSecurityFilterChain;
	
	protected MockHttpSession session;

    protected MockHttpServletRequest request;

	protected MockMvc mockMvc;
 
    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).addFilters(this.springSecurityFilterChain).build();
    }
    
}
