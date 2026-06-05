package com.example.employee.service;

import com.example.employee.common.ErrorCode;
import com.example.employee.dto.EmployeeCreateDTO;
import com.example.employee.dto.EmployeeQueryDTO;
import com.example.employee.dto.EmployeeUpdateDTO;
import com.example.employee.entity.Department;
import com.example.employee.entity.Employee;
import com.example.employee.exception.BusinessException;
import com.example.employee.mapper.DepartmentMapper;
import com.example.employee.mapper.EmployeeMapper;
import com.example.employee.service.impl.EmployeeServiceImpl;
import com.example.employee.vo.PageResultVO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeMapper employeeMapper;

    @Mock
    private DepartmentMapper departmentMapper;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Test
    void createEmployeeRejectsDuplicateEmployeeNo() {
        EmployeeCreateDTO dto = validCreateDto();
        when(employeeMapper.countByEmployeeNo("E001")).thenReturn(1L);

        BusinessException exception = assertThrows(BusinessException.class, () -> employeeService.createEmployee(dto));

        assertEquals(ErrorCode.PARAM_ERROR, exception.getErrorCode());
        assertEquals("员工编号已存在", exception.getMessage());
    }

    @Test
    void createEmployeeRejectsMissingActiveDepartment() {
        EmployeeCreateDTO dto = validCreateDto();
        Department department = new Department();
        department.setId(1L);
        department.setDeleted(1);
        department.setStatus(1);
        when(employeeMapper.countByEmployeeNo("E001")).thenReturn(0L);
        when(departmentMapper.selectById(1L)).thenReturn(department);

        BusinessException exception = assertThrows(BusinessException.class, () -> employeeService.createEmployee(dto));

        assertEquals(ErrorCode.NOT_FOUND, exception.getErrorCode());
        assertEquals("部门不存在", exception.getMessage());
    }

    @Test
    void updateEmployeeRejectsNotFound() {
        EmployeeUpdateDTO dto = validUpdateDto();
        when(employeeMapper.selectById(1L)).thenReturn(null);

        BusinessException exception = assertThrows(BusinessException.class, () -> employeeService.updateEmployee(1L, dto));

        assertEquals(ErrorCode.NOT_FOUND, exception.getErrorCode());
    }

    @Test
    void pageEmployeesMapsDepartmentName() {
        EmployeeQueryDTO query = new EmployeeQueryDTO();
        query.setPage(1);
        query.setPageSize(10);

        Employee employee = new Employee();
        employee.setId(1L);
        employee.setEmployeeNo("E001");
        employee.setName("张三");
        employee.setDepartmentId(2L);
        employee.setGender(1);
        employee.setStatus(1);
        employee.setHireDate(LocalDate.parse("2026-06-01"));

        Department department = new Department();
        department.setId(2L);
        department.setName("技术部");

        when(employeeMapper.countByQuery(query)).thenReturn(1L);
        when(employeeMapper.selectPageByQuery(query, 0L, 10)).thenReturn(List.of(employee));
        when(departmentMapper.selectByIds(List.of(2L))).thenReturn(List.of(department));

        PageResultVO<?> result = employeeService.pageEmployees(query);

        assertEquals(1L, result.getTotal());
        assertEquals(1, result.getRecords().size());
        verify(departmentMapper).selectByIds(List.of(2L));
    }

    @Test
    void deleteEmployeeRejectsMissingRecord() {
        when(employeeMapper.selectById(1L)).thenReturn(null);

        BusinessException exception = assertThrows(BusinessException.class, () -> employeeService.deleteEmployee(1L));

        assertEquals(ErrorCode.NOT_FOUND, exception.getErrorCode());
    }

    private EmployeeCreateDTO validCreateDto() {
        EmployeeCreateDTO dto = new EmployeeCreateDTO();
        dto.setEmployeeNo("E001");
        dto.setName("张三");
        dto.setGender(1);
        dto.setPhone("13800000000");
        dto.setEmail("zhangsan@example.com");
        dto.setDepartmentId(1L);
        dto.setPosition("开发");
        dto.setStatus(1);
        dto.setHireDate(LocalDate.parse("2026-06-01"));
        return dto;
    }

    private EmployeeUpdateDTO validUpdateDto() {
        EmployeeUpdateDTO dto = new EmployeeUpdateDTO();
        dto.setEmployeeNo("E001");
        dto.setName("张三");
        dto.setGender(1);
        dto.setPhone("13800000000");
        dto.setEmail("zhangsan@example.com");
        dto.setDepartmentId(1L);
        dto.setPosition("开发");
        dto.setStatus(1);
        dto.setHireDate(LocalDate.parse("2026-06-01"));
        return dto;
    }
}
