package com.example.employee.service.impl;

import com.example.employee.service.HealthService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class HealthServiceImpl implements HealthService {

    @Override
    public Map<String, String> getHealth() {
        return Map.of("status", "UP");
    }
}
