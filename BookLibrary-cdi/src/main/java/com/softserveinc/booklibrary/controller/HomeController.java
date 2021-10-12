package com.softserveinc.booklibrary.controller;

import com.softserveinc.booklibrary.service.AuthorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

	private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

	private final AuthorService authorService;

	//TODO ?
	public HomeController(AuthorService authorService) {
		this.authorService = authorService;
	}

	@GetMapping(value = "/test")
	public String test() {
		LOGGER.info("User {} with ID 10",
				authorService.getById(10).getFirstName());
		return "home";
	}
}
