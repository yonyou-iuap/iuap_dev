package com.yonyou.login.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yonyou.login.entity.User;
import com.yonyou.login.service.AccountService;


/**
 * 帐号相关Controller
 * @author taomk
 *
 */
@Controller
@RequestMapping("/user")
public class AccountController {
	
	@Autowired
	private AccountService accountService;
	
	@RequestMapping(value = "manage", method = RequestMethod.GET)
	public String jump(Model model) {
		return "userMgr";
	}
	
	@RequestMapping(value = "all", method = RequestMethod.GET)
	public String showAllUser(Model model) {
		List<User> allUsers = accountService.getAllUser();
		model.addAttribute("allUsers" , allUsers);
		return "userView";
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/save")
	public String saveAccount(@ModelAttribute User user, HttpServletResponse response, Model model) throws IOException {
		
		try {
			accountService.registerUser(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "redirect:all";
	}
	
	@RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
	public String showUserById(Model model , @PathVariable String id ) {
		User user = accountService.getUser(new Long(id));
		List<User> allUsers = new ArrayList<>();
		allUsers.add(user);
		model.addAttribute("allUsers" , allUsers);
		return "userView";
	}
}
