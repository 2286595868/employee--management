package com.example.employee.controller;

import com.example.employee.common.ApiResponse;
import com.example.employee.service.DepartmentService;
import com.example.employee.vo.DepartmentVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping
    public ApiResponse<List<DepartmentVO>> list() {
        return ApiResponse.success(departmentService.listActiveDepartments());
    }
}
