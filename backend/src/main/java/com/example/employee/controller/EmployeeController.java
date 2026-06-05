package com.example.employee.controller;

import com.example.employee.common.ApiResponse;
import com.example.employee.dto.EmployeeCreateDTO;
import com.example.employee.dto.EmployeeQueryDTO;
import com.example.employee.dto.EmployeeUpdateDTO;
import com.example.employee.service.EmployeeService;
import com.example.employee.vo.EmployeeVO;
import com.example.employee.vo.PageResultVO;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public ApiResponse<PageResultVO<EmployeeVO>> page(@Valid @ModelAttribute EmployeeQueryDTO query) {
        return ApiResponse.success(employeeService.pageEmployees(query));
    }

    @PostMapping
    public ApiResponse<Void> create(@Valid @RequestBody EmployeeCreateDTO dto) {
        employeeService.createEmployee(dto);
        return ApiResponse.success(null);
    }

    @PutMapping("/{id}")
    public ApiResponse<Void> update(@PathVariable Long id, @Valid @RequestBody EmployeeUpdateDTO dto) {
        employeeService.updateEmployee(id, dto);
        return ApiResponse.success(null);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ApiResponse.success(null);
    }
}
