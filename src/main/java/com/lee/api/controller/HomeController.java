package com.lee.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HomeController {

    @GetMapping("/")
    public Map<String, Object> home() {
        Map<String, Object> response = new HashMap<>();
        response.put("name", "QR Code & Validator API");
        response.put("version", "1.0.0");
        response.put("status", "running");
        
        Map<String, String> endpoints = new HashMap<>();
        endpoints.put("QR Code Generation", "POST /api/v1/qrcode/generate");
        endpoints.put("Email Validation", "POST /api/v1/validate/email");
        endpoints.put("Phone Validation", "POST /api/v1/validate/phone");
        
        response.put("endpoints", endpoints);
        response.put("documentation", "https://rapidapi.com/lee/api/lee");
        
        return response;
    }
}