package com.thalasoft.learnintouch.web.rest.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.thalasoft.learnintouch.data.jpa.domain.Admin;
import com.thalasoft.learnintouch.data.service.event.admin.CreatedAdminEvent;
import com.thalasoft.learnintouch.data.service.event.admin.CreateAdminEvent;
import com.thalasoft.learnintouch.data.service.event.admin.EventAdmin;
import com.thalasoft.learnintouch.data.service.jpa.AdminService;
import com.thalasoft.learnintouch.web.rest.utils.Common;

public class AdminControllerTest extends AbstractControllerTest {

  private static Logger logger = LoggerFactory.getLogger(AdminControllerTest.class);

  private static final String PASSWORD = "mypassword";

  @Autowired
  AdminService adminService;

  // @Autowired
  // private RestTemplate restTemplate;

  private Admin admin0;
  private List<Admin> manyAdmins;

  @Before
  public void beforeAnyTest() throws Exception {
    admin0 =
        new Admin.AdminBuilder("Stephane", "Eybert", "mittiprovence@yahoo.se", "stephane").setPassword("e41de4c55873f9c000f4cdaac6efd3aa")
            .setPasswordSalt("").build();

    CreatedAdminEvent adminCreatedEvent0 = adminService.add(new CreateAdminEvent(EventAdmin.newFrom(admin0)));
    admin0.setId(adminCreatedEvent0.getAdminId());

    manyAdmins = new ArrayList<Admin>();
    for (int i = 0; i < 99; i++) {
      String index = intToString(i, 2);
      Admin oneAdmin =
          new Admin.AdminBuilder("zfirstname" + index, "zlastname" + index, "zemail@thalasoft.com" + index, "zlogin" + index)
              .setPassword("zpassword" + index).setPasswordSalt("").build();
      manyAdmins.add(oneAdmin);
      CreatedAdminEvent oneAdminCreatedEvent = adminService.add(new CreateAdminEvent(EventAdmin.newFrom(oneAdmin)));
      oneAdmin.setId(oneAdminCreatedEvent.getAdminId());
    }
  }

  private String intToString(int num, int digits) {
    String output = Integer.toString(num);
    while (output.length() < digits) {
      output = "0" + output;
    }
    return output;
  }

  @Test
  public void testJsonHola() throws Exception {
    HttpHeaders httpHeaders = Common.createAuthenticationHeaders("stephane" + ":" + PASSWORD);

    this.mockMvc.perform(get("/admin/json/hola").headers(httpHeaders)).andExpect(status().isOk()).andExpect(content().string("Hola !"));
  }

  @Test
  public void testHello() throws Exception {
    HttpHeaders httpHeaders = Common.createAuthenticationHeaders("stephane" + ":" + PASSWORD);

    this.mockMvc.perform(get("/admin/hello").headers(httpHeaders).accept(MediaType.TEXT_HTML)).andExpect(status().isOk());
  }

  @Test
  public void testJsonShow() throws Exception {
    HttpHeaders httpHeaders = Common.createAuthenticationHeaders("stephane" + ":" + PASSWORD);

    mockMvc.perform(get("/admin/json/show/" + admin0.getId()).headers(httpHeaders).accept(MediaType.APPLICATION_JSON)).andExpect(
        status().isOk());

    mockMvc.perform(get("/admin/json/show/{id}", admin0.getId()).headers(httpHeaders).accept(MediaType.APPLICATION_JSON)).andExpect(
        status().isOk());

    mockMvc.perform(get("/admin/json/show/{id}", admin0.getId()).headers(httpHeaders).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk()).andExpect(content().contentType(org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE))
        .andExpect(jsonPath("firstname").value(admin0.getFirstname())).andExpect(jsonPath("email").value(admin0.getEmail()))
        .andExpect(jsonPath("profile").value(admin0.getProfile())).andExpect(jsonPath("superAdmin").value(admin0.isSuperAdmin()))
        .andExpect(jsonPath("id").value(new Integer(admin0.getId().intValue())));
  }

  @Test
  public void testJsonList() throws Exception {
    HttpHeaders httpHeaders = Common.createAuthenticationHeaders("stephane" + ":" + PASSWORD);

    mockMvc.perform(get("/admin/json/list").headers(httpHeaders).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
        .andExpect(content().contentType(org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE))
        .andExpect(jsonPath("$[0].firstname").value(admin0.getFirstname()));
  }

  @Test
  public void testJsonSearch() throws Exception {
    HttpHeaders httpHeaders = Common.createAuthenticationHeaders("stephane" + ":" + PASSWORD);

    mockMvc.perform(get("/admin/json/search/{searchTerm}", "irstnam").headers(httpHeaders).accept(MediaType.APPLICATION_JSON))
        .andDo(print()).andExpect(status().isOk()).andExpect(content().contentType(org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE))
        .andExpect(jsonPath("$[0].firstname").value(manyAdmins.get(0).getFirstname()));
  }

  @Test
  public void testHttpHeaderListsValidHttpMethodsOnInvalidHttpMethodSentToValidResourceUri() {}

  @Test
  public void testResponseLocationHttpHeaderContainsUriOfNewlyCreatedResource() {}

  // mockMvc.perform(get("/people").accept(MediaType.APPLICATION_JSON))
  // .andExpect(jsonPath("$.links[?(@.rel == 'self')].href").value("http://localhost:8080/people"));
  // mockMvc.perform(get("/handle").accept(MediaType.APPLICATION_XML))
  // .andExpect(xpath("/person/ns:link[@rel='self']/@href",
  // ns).string("http://localhost:8080/people"));

  // page.page The page you want to retrieve
  // page.size The size of the page you want to retrieve
  // page.sort The property that should be sorted by
  // page.sort.dir The direction that should be used for sorting

  // session.setAttribute("sessionParm", admin0.getId())
  // .session(session)
  // RequestContextHolder.setRequestAttributes(new
  // ServletRequestAttributes(request));

  // String uri = "http://localhost:8080/learnintouch-web/admin/find/{id}";
  // EventAdmin admin = restTemplate.getForObject(uri, EventAdmin.class,
  // admin0.getId());
  // assertNotNull(admin);
  // assertEquals(admin0.getFirstname(), admin.getFirstname());

  // this.mockMvc.perform(post("/pcusers/create/")
  // .content(newUser.toString()) // <-- sets the request content !
  // .accept(MediaType.APPLICATION_JSON)
  // .andExpect(status().isOk());

}
