package com.thalasoft.learnintouch.web.rest.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.thalasoft.learnintouch.data.jpa.domain.Admin;
import com.thalasoft.learnintouch.data.jpa.repository.AdminRepository;
import com.thalasoft.learnintouch.data.service.jpa.AdminService;
import com.thalasoft.learnintouch.web.utils.PageWrapper;

@Controller
@RequestMapping("admin")
public class AdminController {

    private static Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired 
	private AdminService adminService;

	@Autowired
	private AdminRepository adminRepository;
	
    @RequestMapping(value = "json/hola")
	@Transactional(readOnly = true)
	@ResponseBody
    public String holaTodos() {
        String message = "Hola !";
        return message;
    }
    
    @RequestMapping(value = "hello")
	@Transactional(readOnly = true)
    public ModelAndView helloWorld() {
        String message = "<div align='center'><h1>Hello World, Spring 3.2.2<h1></div>";
        return new ModelAndView("hello", "message", message);
    }
    
    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String login(ModelMap model) {
        return "admin/login";
    }
 
    @RequestMapping(value = "denied", method = RequestMethod.GET)
    public String denied(ModelMap model) {
        model.addAttribute("failed", "true");
        return "admin/denied";
    }
 
    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public String logout(ModelMap model) {
        return "admin/logout";
    }

	@RequestMapping(value = "json/show/{id}", method = RequestMethod.GET)
	@Transactional(readOnly = true)
	@ResponseBody
	public Admin show(@PathVariable("id") Long id) {
		return adminRepository.findOne(id);
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String defaultPage(ModelMap map) {
	    return "redirect:admin/list";
	}
	
    @RequestMapping(value = "list")
    @Transactional(readOnly = true)
    public String list(Model model, Pageable pageable) {
        PageWrapper<Admin> page = new PageWrapper<Admin>(adminRepository.findAll(pageable), "list");
        model.addAttribute("page", page);
        return "admin/list";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    @Transactional(readOnly = true)
    public String add(@ModelAttribute(value = "admin") Admin admin) {
        adminRepository.save(admin);
        return "redirect:admin/list";
    }

    @RequestMapping("delete/{adminId}")
    @Transactional
    public String deleteEmplyee(@PathVariable("adminId") Long adminId) {
        adminRepository.deleteById(adminId);
        return "redirect:admin/list";
    }
    
    @RequestMapping(value = "json/list")
    @Transactional(readOnly = true)
    @ResponseBody
    public List<Admin> jsonList(Pageable pageable) {
        Page<Admin> page = adminRepository.findAll(pageable);
        List<Admin> admins = page.getContent();
        return admins;
    }

    @RequestMapping(value = "json/search/{searchTerm}", method = RequestMethod.GET)
    @Transactional(readOnly = true)
    @ResponseBody
    public List<Admin> jsonSearch(@PathVariable("searchTerm")  String searchTerm, Pageable pageable) {
        Page<Admin> page = adminService.search(searchTerm, pageable);
        return page.getContent();
    }

	@RequestMapping(value = "xml/{adminId}", method = RequestMethod.GET, produces = "application/xml")
	@Transactional(readOnly = true)
    @ResponseBody
	public String xml(@RequestParam("adminId") Long adminId) {
		String xml = null;
		return xml;
	}

	@RequestMapping(value = "json/save", method = RequestMethod.POST)
	@Transactional
	@ResponseBody
	public Admin save(@RequestBody Admin admin) {
		adminRepository.save(admin);
		return admin;
	}

}
