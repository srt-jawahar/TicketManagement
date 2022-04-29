package com.foucsr.ticketmanager.mysql.database.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

	@RequestMapping(value = "/")
	public String homeDefault(ModelMap modelMap) {

		return "index";
	}
	

	@RequestMapping(value = "/index")
	public String homePage(ModelMap modelMap) {

		return "index";
	}
		
	@RequestMapping(value = "/resetPwd/{token}")
	public String forgetPassword(ModelMap modelMap) {

		return "index";
	}
	
}