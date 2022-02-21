package com.softserveinc.booklibrary.backend.controller;

import javax.servlet.ServletContext;

import com.softserveinc.booklibrary.backend.configuration.HibernateConfiguration;
import com.softserveinc.booklibrary.backend.configuration.MvcConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.Assert;
import org.springframework.web.context.WebApplicationContext;


@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {
		MvcConfiguration.class
})
class AuthorControllerTest {

	@Autowired
	private WebApplicationContext context;

	private MockMvc mvc;

	@BeforeEach
	public void setup() {
		this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();
	}

	@Test
	public void givenWebAppContext_WhenServletContext_ThenItProvidesAuthorController() {
		ServletContext servletContext = context.getServletContext();
		Assert.notNull(servletContext, "Context is NULL !!!");
		Assert.isTrue(servletContext instanceof MockServletContext, "FALSE");
		Assert.notNull(context.getBean("authorController"), "AuthorController is NULL !!!");
	}

}
