package com.example.employee.controller;

import com.example.employee.common.ApiResponse;
import com.example.employee.service.HealthService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/health")
public class HealthController {

    private final HealthService healthService;

    public HealthController(HealthService healthService) {
        this.healthService = healthService;
    }

    @GetMapping
    public ApiResponse<Map<String, String>> health() {
        return ApiResponse.success(healthService.getHealth());
    }
}
