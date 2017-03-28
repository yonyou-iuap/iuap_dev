package com.yonyou.iuap.rest;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.Assert;
import org.springframework.web.context.WebApplicationContext;

import com.yonyou.iuap.persistence.utils.IDGenerator;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration(value = "src/main/webapp")
@ContextConfiguration(locations = { "classpath:applicationContext.xml","file:src/main/webapp/WEB-INF/spring-mvc.xml" })
public class SpringmvcRestTest {

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		this.mockMvc = webAppContextSetup(this.wac).build();
	}
	
	@Test  
	public void testSpringMvcRest() throws Exception {  
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/rest/demo/testrestful")).andReturn();  
		System.out.println(result.getResponse().getContentAsString());
		Assert.notNull(result);
	}
	
	@Test  
	public void testOidRest() throws Exception { 
		String id=IDGenerator.generateObjectID(null);
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/rest/demo/oid/"+id)).andReturn();  
		System.out.println(result.getResponse().getContentAsString());
		Assert.notNull(result);
	}
}
