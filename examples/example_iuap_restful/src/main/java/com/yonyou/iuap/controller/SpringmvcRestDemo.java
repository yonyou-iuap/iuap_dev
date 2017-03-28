package com.yonyou.iuap.controller;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yonyou.iuap.entity.DemoEntity;
import com.yonyou.iuap.persistence.utils.IDGenerator;

@Controller
@RequestMapping(value = "/rest/demo")
public class SpringmvcRestDemo {
	
	@RequestMapping(value="testrestful", method=RequestMethod.GET)  
	public @ResponseBody DemoEntity testrestful(HttpServletRequest request, HttpServletResponse response) { 
		DemoEntity vo = new DemoEntity();
		vo.setId(UUID.randomUUID().toString());
		return vo;
	}
	
	@RequestMapping(value="oid/{id}", method=RequestMethod.GET)  
	public @ResponseBody DemoEntity getDemo(@PathVariable("id") String id, Model model) { 
		DemoEntity vo = new DemoEntity();
		
		vo.setId(id);
		return vo;
	}
	
}


