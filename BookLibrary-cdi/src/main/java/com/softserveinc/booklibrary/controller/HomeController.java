package com.softserveinc.booklibrary.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

	@GetMapping(value="/test")
	public String test() {
		return "home";
	}
}
