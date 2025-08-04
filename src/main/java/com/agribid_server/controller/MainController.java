package com.agribid_server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @GetMapping("welcome")
    public String keepAlive() {
        return "Welcome to AgriBid";
    }
}
