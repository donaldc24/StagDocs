package com.stagdocs.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    @GetMapping("/api/healthcheck")
    public String healthCheck() {
        return "Health check passed!";
    }

    @GetMapping("/check/login")
    public String checkTest() {
        return "Accessed";
    }
}
