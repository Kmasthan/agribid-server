package com.agribid_server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class MainController {

	@GetMapping("welcome")
	public String welcome() {
		return "Welcome to spring boot";
	}
}
