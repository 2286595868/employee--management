package com.example.employee.service;

import com.example.employee.dto.EmployeeCreateDTO;
import com.example.employee.dto.EmployeeQueryDTO;
import com.example.employee.dto.EmployeeUpdateDTO;
import com.example.employee.vo.EmployeeVO;
import com.example.employee.vo.PageResultVO;

public interface EmployeeService {

    PageResultVO<EmployeeVO> pageEmployees(EmployeeQueryDTO query);

    void createEmployee(EmployeeCreateDTO dto);

    void updateEmployee(Long id, EmployeeUpdateDTO dto);

    void deleteEmployee(Long id);
}
